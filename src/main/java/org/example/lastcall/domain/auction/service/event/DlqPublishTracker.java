package org.example.lastcall.domain.auction.service.event;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class DlqPublishTracker {

  private final Cache<String, Boolean> returnedCorrelationIds =
      Caffeine.newBuilder()
          .expireAfterWrite(10, TimeUnit.SECONDS)  // 10초 후 자동 삭제
          .build();

  public Cache<String, Boolean> getReturnedCorrelationIds() {
    return returnedCorrelationIds;
  }

  public void markAsReturned(String corrId) {
    returnedCorrelationIds.put(corrId, true);
  }

  public boolean isReturned(String corrId) {
    return returnedCorrelationIds.getIfPresent(corrId) != null;
  }

}
