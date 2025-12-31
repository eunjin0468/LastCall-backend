package org.example.lastcall.domain.auction.service.event;

import com.rabbitmq.client.Channel;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
import org.example.lastcall.common.exception.BusinessException;
import org.example.lastcall.domain.auction.entity.Auction;
import org.example.lastcall.domain.auction.exception.AuctionErrorCode;
import org.example.lastcall.domain.auction.repository.AuctionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.lastcall.domain.auction.enums.AuctionEventType.END;
import static org.example.lastcall.domain.auction.enums.AuctionEventType.INVALID;
import static org.example.lastcall.domain.auction.enums.AuctionEventType.START;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionEventProcessorTest {

    @InjectMocks
    private AuctionEventProcessor auctionEventProcessor;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Channel channel;

    @Mock
    private Message message;

    @Mock
    private MessageProperties messageProperties;

    @Mock
    private Consumer<Long> auctionHandler;

    @Test
    @DisplayName("processEvent - 정상 처리 시 ACK 처리된다")
    void processEvent_정상_처리_시_ACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(auctionHandler).should().accept(auctionId);
        then(channel).should().basicAck(1L, false);
        then(channel).should(never()).basicNack(anyLong(), anyBoolean(), anyBoolean());
    }

    @Test
    @DisplayName("processEvent - 버전 불일치 시 이벤트를 무시하고 ACK 처리된다")
    void processEvent_버전_불일치_시_이벤트를_무시하고_ACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long eventVersion = 1L;
        Long currentVersion = 2L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, eventVersion);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(currentVersion);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(auctionHandler).should(never()).accept(anyLong());
        then(channel).should().basicAck(1L, false);
    }

    @Test
    @DisplayName("processEvent - 비즈니스 예외 발생 시 ACK 처리된다")
    void processEvent_비즈니스_예외_발생_시_ACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));
        willThrow(new BusinessException(AuctionErrorCode.AUCTION_NOT_FOUND))
                .given(auctionHandler).accept(auctionId);

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(channel).should().basicAck(1L, false);
        then(channel).should(never()).basicNack(anyLong(), anyBoolean(), anyBoolean());
    }

    @Test
    @DisplayName("processEvent - 시스템 예외 발생 시 재시도 횟수가 3 미만이면 NACK 처리된다")
    void processEvent_시스템_예외_발생_시_재시도_횟수가_3_미만이면_NACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        // x-death 헤더 설정: retryCount = 2
        Map<String, Object> xDeathEntry = Map.of(
                "queue", AuctionRabbitMqConfig.AUCTION_START_QUEUE,
                "count", 2L
        );
        given(messageProperties.getHeaders()).willReturn(Map.of("x-death", List.of(xDeathEntry)));

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(auctionId);

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(channel).should().basicNack(1L, false, false);
        then(channel).should(never()).basicAck(anyLong(), anyBoolean());
        then(rabbitTemplate).should(never()).convertAndSend(anyString(), anyString(), any(), any(), any());
    }

    @Test
    @DisplayName("processEvent - 시스템 예외 발생 시 재시도 횟수가 3 이상이면 DLQ로 전송하고 ACK 처리된다")
    void processEvent_시스템_예외_발생_시_재시도_횟수가_3_이상이면_DLQ로_전송하고_ACK_처리된다() throws Exception {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        // x-death 헤더 설정: retryCount = 3
        Map<String, Object> xDeathEntry = Map.of(
                "queue", AuctionRabbitMqConfig.AUCTION_START_QUEUE,
                "count", 3L
        );
        given(messageProperties.getHeaders()).willReturn(Map.of("x-death", List.of(xDeathEntry)));

        // confirmTimeoutMs 설정
        ReflectionTestUtils.setField(auctionEventProcessor, "confirmTimeoutMs", 5000L);

        // DLQ 전송 모킹 - ArgumentCaptor로 CorrelationData 캡처
        ArgumentCaptor<CorrelationData> cdCaptor = ArgumentCaptor.forClass(CorrelationData.class);
        willAnswer(invocation -> {
            CorrelationData cd = invocation.getArgument(4, CorrelationData.class);
            // CorrelationData의 Future를 완료 상태로 설정
            CorrelationData.Confirm confirm = new CorrelationData.Confirm(true, null);
            CompletableFuture<CorrelationData.Confirm> future = new CompletableFuture<>();
            future.complete(confirm);
            ReflectionTestUtils.setField(cd, "future", future);
            return null;
        }).given(rabbitTemplate).convertAndSend(anyString(), anyString(), any(), any(), cdCaptor.capture());

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(anyLong());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(rabbitTemplate).should().convertAndSend(
                eq(AuctionRabbitMqConfig.AUCTION_DLX),
                eq(AuctionRabbitMqConfig.AUCTION_START_DLQ_KEY),
                eq(event),
                any(),
                any()

        );
        then(channel).should().basicAck(1L, false);
        then(channel).should(never()).basicNack(anyLong(), anyBoolean(), anyBoolean());
    }

    @Test
    @DisplayName("processEvent - START 이벤트는 START DLQ 라우팅 키를 사용한다")
    void processEvent_START_이벤트는_START_DLQ_라우팅_키를_사용한다() throws Exception {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        Map<String, Object> xDeathEntry = Map.of(
                "queue", AuctionRabbitMqConfig.AUCTION_START_QUEUE,
                "count", 3L
        );
        given(messageProperties.getHeaders()).willReturn(Map.of("x-death", List.of(xDeathEntry)));

        // confirmTimeoutMs 설정
        ReflectionTestUtils.setField(auctionEventProcessor, "confirmTimeoutMs", 5000L);

        // DLQ 전송 모킹
        willAnswer(invocation -> {
            CorrelationData cd = invocation.getArgument(4, CorrelationData.class);
            CorrelationData.Confirm confirm = new CorrelationData.Confirm(true, null);
            CompletableFuture<CorrelationData.Confirm> future = new CompletableFuture<>();
            future.complete(confirm);
            ReflectionTestUtils.setField(cd, "future", future);
            return null;
        }).given(rabbitTemplate).convertAndSend(anyString(), anyString(), any(), any(), any(CorrelationData.class));

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(anyLong());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        then(rabbitTemplate).should().convertAndSend(
                eq(AuctionRabbitMqConfig.AUCTION_DLX),
                routingKeyCaptor.capture(),
                eq(event),
                any(),
                any()
        );
        assertThat(routingKeyCaptor.getValue()).isEqualTo(AuctionRabbitMqConfig.AUCTION_START_DLQ_KEY);
    }

    @Test
    @DisplayName("processEvent - END 이벤트는 END DLQ 라우팅 키를 사용한다")
    void processEvent_END_이벤트는_END_DLQ_라우팅_키를_사용한다() throws Exception {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        Map<String, Object> xDeathEntry = Map.of(
                "queue", AuctionRabbitMqConfig.AUCTION_END_QUEUE,
                "count", 3L
        );
        given(messageProperties.getHeaders()).willReturn(Map.of("x-death", List.of(xDeathEntry)));

        // confirmTimeoutMs 설정
        ReflectionTestUtils.setField(auctionEventProcessor, "confirmTimeoutMs", 5000L);

        // DLQ 전송 모킹
        willAnswer(invocation -> {
            CorrelationData cd = invocation.getArgument(4, CorrelationData.class);
            CorrelationData.Confirm confirm = new CorrelationData.Confirm(true, null);
            CompletableFuture<CorrelationData.Confirm> future = new CompletableFuture<>();
            future.complete(confirm);
            ReflectionTestUtils.setField(cd, "future", future);
            return null;
        }).given(rabbitTemplate).convertAndSend(anyString(), anyString(), any(), any(), any(CorrelationData.class));

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(anyLong());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 종료", END);

        // then
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        then(rabbitTemplate).should().convertAndSend(
                eq(AuctionRabbitMqConfig.AUCTION_DLX),
                routingKeyCaptor.capture(),
                eq(event),
                any(),
                any()
        );
        assertThat(routingKeyCaptor.getValue()).isEqualTo(AuctionRabbitMqConfig.AUCTION_END_DLQ_KEY);
    }

    @Test
    @DisplayName("processEvent - x-death 헤더가 없으면 retryCount는 0으로 계산된다")
    void processEvent_x_death_헤더가_없으면_retryCount는_0으로_계산된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));
        given(messageProperties.getHeaders()).willReturn(Map.of());

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(auctionId);

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(channel).should().basicNack(1L, false, false);
        then(rabbitTemplate).should(never()).convertAndSend(anyString(), anyString(), any(), any(), any());
    }

    @Test
    @DisplayName("processEvent - 잘못된 queueType은 IllegalArgumentException을 발생시키고 ACK 처리된다")
    void processEvent_잘못된_queueType은_IllegalArgumentException을_발생시키고_ACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);

        // when & then
        assertThatThrownBy(() ->
                auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매", INVALID)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported queueType");

        then(channel).should().basicAck(1L, false);
    }

    @Test
    @DisplayName("processEvent - ACK 실패 시 IOException을 로깅하고 예외를 전파하지 않는다")
    void processEvent_ACK_실패_시_IOException을_로깅하고_예외를_전파하지_않는다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));

        willThrow(new IOException("ACK 실패")).given(channel).basicAck(anyLong(), anyBoolean());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(auctionHandler).should().accept(auctionId);
        then(channel).should().basicAck(1L, false);
    }

    @Test
    @DisplayName("processEvent - NACK 실패 시 IOException을 로깅하고 예외를 전파하지 않는다")
    void processEvent_NACK_실패_시_IOException을_로깅하고_예외를_전파하지_않는다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        Auction auction = mock(Auction.class);
        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auction.getEventVersion()).willReturn(version);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.of(auction));
        given(messageProperties.getHeaders()).willReturn(Map.of());

        willThrow(new RuntimeException("시스템 예외"))
                .given(auctionHandler).accept(auctionId);
        willThrow(new IOException("NACK 실패"))
                .given(channel).basicNack(anyLong(), anyBoolean(), anyBoolean());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(channel).should().basicNack(1L, false, false);
    }

    @Test
    @DisplayName("processEvent - 경매를 찾을 수 없으면 BusinessException을 발생시키고 ACK 처리된다")
    void processEvent_경매를_찾을_수_없으면_BusinessException을_발생시키고_ACK_처리된다() throws IOException {
        // given
        Long auctionId = 1L;
        Long version = 1L;
        AuctionEvent event = new AuctionEvent(auctionId, null, null, null, version);

        given(message.getMessageProperties()).willReturn(messageProperties);
        given(messageProperties.getDeliveryTag()).willReturn(1L);
        given(auctionRepository.findById(auctionId)).willReturn(Optional.empty());

        // when
        auctionEventProcessor.processEvent(event, message, channel, auctionHandler, "경매 시작", START);

        // then
        then(auctionHandler).should(never()).accept(anyLong());
        then(channel).should().basicAck(1L, false);
    }
}