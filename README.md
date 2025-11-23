# LastCall! – 실시간 경매 플랫폼         

🛍️ "이 상품, 내가 꼭 낙찰받고 싶다!"  
하지만 입찰이 어떻게 진행되는지 실시간으로 확인하기 어렵고, 언제 경매가 끝나는지도 불분명한 경우가 많아요.

💸 “입찰했는데 금액이 반영이 안 됐어…”  
입찰 시점 **동시성 문제**나 **데이터 지연**으로 인해 공정한 경쟁이 어려울 때도 있죠.

⏰ “경매 시간을 내가 직접 정할 수 있으면 좋을 텐데…”  
정해진 시간대에만 진행되는 플랫폼은 사용자 입장에서 아쉬움이 많습니다.

그래서 저희는 만들었습니다.  
**“Last Call!"**  *제한된 시간 동안 실시간으로 입찰이 진행되는 온라인 경매 플랫폼*

- "사용자가 직접 경매의 시작/종료 시간을 설정할 수 있어요"
- "입찰은 실시간으로 반영되며, 현재 최고가를 바로 확인할 수 있어요"
- "사용자는 포인트를 충전하고, 보유 포인트 내에서 자유롭게 입찰할 수 있어요."
- "경매가 종료되면 시스템이 자동으로 최고 입찰가를 확인해 낙찰 내역을 기록합니다."

---

## 📌프로젝트 소개 <a id="프로젝트-소개"></a>
<img width="600" height="600" alt="image" src="https://github.com/user-attachments/assets/8c374a84-7997-433e-bdd9-73f1a285e265" />

LastCall은 상품 등록부터 입찰, 예약, 낙찰 정산까지의 과정을 자동으로 처리해 주는 실시간 경매 플랫폼이에요.  
사용자는 경매를 직접 관리할 필요 없이, 입찰과 결과만 편하게 확인할 수 있어요.  
- 사용자는 원하는 시간에 경매의 시작/종료 시점을 직접 설정할 수 있고,
- 입찰은 서버에서 실시간으로 정확하게 반영되어, 현재 최고가를 바로 확인할 수 있어요.
- RabbitMQ 예약 메시지를 이용해 경매는 지정된 시간에 자동으로 시작/종료됩니다.
- 경매가 끝나면 시스템이 알아서 처리해요:
    - 최고 입찰자를 낙찰자로 확정
    - 유찰자에게는 예치 포인트 즉시 환불
    - 낙찰자의 포인트는 정산 포인트로 이동

사용자는 입찰에만 집중하면 되고, 나머지 과정은 모두 시스템이 알아서 처리하는 구조로 되어 있어요.

---

## 🛠️ Tech Stack

### 🧩 Backend
![Java 17](https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![QueryDSL](https://img.shields.io/badge/QueryDSL-005C84?style=for-the-badge) ![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

### 🔐 Authentication
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

### 🗄️ Database & In-memory
![MySQL](https://img.shields.io/badge/MySQL_8-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

### ☁️ Infra · DevOps
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white) ![AWS VPC](https://img.shields.io/badge/AWS_VPC-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![AWS ECS](https://img.shields.io/badge/AWS_ECS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![AWS ECR](https://img.shields.io/badge/AWS_ECR-FF9900?style=for-the-badge&logo=amazonecr&logoColor=white) ![AWS ALB](https://img.shields.io/badge/AWS_ALB-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![AWS IAM](https://img.shields.io/badge/AWS_IAM-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![AWS Route 53](https://img.shields.io/badge/AWS_Route_53-8C4FFF?style=for-the-badge&logo=amazonaws&logoColor=white) ![AWS NAT Gateway](https://img.shields.io/badge/AWS_NAT_Gateway-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![AWS S3](https://img.shields.io/badge/AWS_S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white) ![AWS RDS](https://img.shields.io/badge/AWS_RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-000000?style=for-the-badge&logo=githubactions&logoColor=white)

### 📈 Monitoring
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white) ![Grafana](https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white)

### ⚙️ Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white) ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/GitHub-000000?style=for-the-badge&logo=github&logoColor=white) ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ![nGrinder](https://img.shields.io/badge/nGrinder-000000?style=for-the-badge)

### 💬 Collaboration
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white) ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white) ![ERDCloud](https://img.shields.io/badge/ERD_Cloud-0D96F6?style=for-the-badge&logoColor=white) ![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) ![ZEP](https://img.shields.io/badge/ZEP-6C4BF4?style=for-the-badge&logoColor=white)


## ☑️ System Architecture
<img width="600" height="600" alt="image" src="https://github.com/user-attachments/assets/f45f7f95-c0d6-4cae-a7f5-f0291574032b" />

## ⚙️ 경매 핵심 기능 구조 
### ⏰ 실시간 입찰 동시성 제어 (Redis 분산 락)
- 입찰 시 Redis 분산 락 적용
- 동시 입찰 시 단 한 명만 성공하도록 동시성 제어 처리
- 포인트 예치/해제 시 동시성 보장

### ✔ 자동 경매 스케줄링 (RabbitMQ Delay Queue)
- 경매 생성 시 시작·종료 시각에 맞춰 Delay Queue에 메시지 예약
- 시작 시점 도달 → 경매 자동 시작
- 종료 시점 도달 → 종료 이벤트 트리거 실행

### ✔ 낙찰 자동 확정 및 정산 로직
- 종료 이벤트 발생 시 최고 입찰가 기반으로 자동 낙찰
- 낙찰자는 예치 포인트가 정산 포인트로 전환
- 유찰자는 예치 포인트 전액 즉시 환불

## 💳 포인트 관리
### 🔐 사용자 단위 락 기반 포인트 충전
- 포인트 충전 시 `@DistributedLock` 적용으로 중복 충전 방지
- Redis 락으로 포인트 증가 연산의 원자성 보장
- 충전 후 `@CacheEvict("userPoints")`로 캐시 무효화하여 최신 상태 유지

### 💰 포인트 예치/정산
- 입찰 시 예치 포인트를 락 기반으로 안전하게 처리
- 최초 입찰은 전체 금액 예치, 재입찰은 증가 금액만 추가 예치
- 종료 시 자동 정산: 낙찰자는 정산 포인트 이동, 유찰자는 즉시 환불


---

## 🗃ERD <a id="erd"></a>
<img width="1527" height="813" alt="image" src="https://github.com/user-attachments/assets/1ac04cee-d4e6-4eed-a0dd-4f64c20bd5c1" />

---

## 📄 API Overview <a id="api-명세서"></a>
- Auth API  
- User API  
- Auction API  
- Bid API  
- Point API  
[👉 API 명세서 보러가기](https://www.notion.so/API-2b406f466a3c80f9802afa8b8b688362?source=copy_link)

---

## 🖼️와이어 프레임 <a id="와이어-프레임"></a>
<img width="11568" height="5055" alt="image" src="https://github.com/user-attachments/assets/50580245-4504-470f-8262-a76cfe6b0f68" />  

[👉 Figma](https://www.figma.com/design/MXc4uESnjz8e0S8crq0Qi1/%EA%B2%BD%EB%A7%A4?node-id=0-1&p=f&m=draw)


---

## ☁️인프라 아키텍처 <a id="인프라-아키텍처"></a>
<img width="991" height="747" alt="Image" src="https://github.com/user-attachments/assets/ada70ad3-8bbe-4874-9705-756161d1e367" />

## 🧪테스트 <a id="테스트"></a>
<img width="746" height="354" alt="스크린샷 2025-11-18 오후 2 33 32" src="https://github.com/user-attachments/assets/01c3b0cf-5048-4f22-91f2-cafd239e312d" />  

### 단위 테스트
- 각 도메인 별 서비스 단위 테스트 작성 후 검증
   - 기준: Jacoco 커버리지 60% 이상
   - Mockito 기반 Mock 객체  
   - Redis/RabbitMQ 실제 의존성 제거
     
### 통합 테스트(분산락)
- 분산락 적용한 Point, Bid, Auction 서비스는 통합 테스트로 추가 검증
   - 기준: Jacoco 커버리지 60% 이상

## ⚙️성능 테스트 <a id="성능-테스트"></a>
### 🔧Index 성능 개선
**1️⃣ 부하테스트 시나리오**
- 대상API : `GET /api/v1/auctions/me/selling`
- 인덱스 구성 : `(user_id, deleted, created_at DESC)`
- 환경 : nGrinder Controller 1대 / Agent 1대 / Spring Boot 서버 (9090)
- 목적 : 로그인한 사용자가 `내가 판매한 경매 목록 조회` API 호출 시, 인덱스 적용 전/후의 성능 차이를 측정 및 분석
- 적용 이유 : 데이터가 많아질 경우 테이블 full scan이 빈번하게 발생할 것으로 예상됨  

**2️⃣ 결과**  
<img width="1201" height="433" alt="image" src="https://github.com/user-attachments/assets/fd4e2f11-8827-4765-8628-2ade851ad43a" />
<img width="532" height="149" alt="image" src="https://github.com/user-attachments/assets/5bdb58dc-b0c5-4b49-bd42-f051032b8bc2" />

**3️⃣ 해석**

**① 인덱스 적용 전**  
- TPS(초당 처리량)가 100~350TPS 사이에서 지속적으로 변동
- 일부 구간에서는 150TPS 이하로 급락하는 불안정한 패턴을 보임
- user_id, deleted, created_at 조건에 대한 풀 스캔으로 인한 결과로 분석됨 

**② 인덱스 적용 후**  
- TPS가 200~400TPS 범위에서 비교적 안정적으로 유지
- 평균 처리량은 227.9TPS → 334.4TPS (**🚀 +46%**) 로 향상됨
- 응답 시간 편차 줄어들어 일관성 있고 예측 가능한 성능 확보


### 🔧Redis Cache 성능 개선
**1️⃣ 부하테스트 시나리오**
- 대상API : `GET /api/v1/users/points`
- 환경 : nGrinder Controller 1대 / Agent 1대 / Spring Boot 서버 (9090)
- 목적 : 로그인한 사용자가 `내 포인트 조회` API 호출 시, Redis Cache 적용 전/후의 성능 차이를 측정 및 분석
- 적용 이유 : 포인트 조회는 조회 빈도는 높지만 데이터 변경은 비교적 적은 정적 요청임
- 캐시 TTL : 1시간
   	- 짧은 TTL로 인한 재적재 부하 방지
   	- 충분한 긴 캐시 유지로 Redis hit 비율 높임
   	- 포인트 변경 시 캐시 무효화(`@CacheEvict`)로 최신성 보장

**2️⃣ 결과**  
<img width="2400" height="866" alt="image" src="https://github.com/user-attachments/assets/10d70b67-7ad5-4a1b-86d8-bc6f1981d30d" />
<img width="533" height="148" alt="image" src="https://github.com/user-attachments/assets/5c9bffc2-b5d2-47e5-b0f1-f2fa5abdfe04" />

**3️⃣ 해석**

**① 캐시 적용 전**  
- TPS 불안정하고 주기적으로 하락 구간 반복
- 평균 응답시간(MTT) 역시 변동 폭 크게 나타났으며, 특정 구간에서 응답시간 일시적 증가 및 TPS 감소 패턴 관찰
- DB 조회 요청 집중되면서 I/O 대기 등으로 인한 병목 현상 발생된 것으로 분석됨<br>

**② 캐시 적용 후**  
- TPS와 응답 속도 일정 유지
- DB 부하 감소 및 응답 일관성 확보
- TPS 약 31.7% 향상
- 평균 응답 시간 약 2.8배 단축

## 📂프로젝트 구조 <a id="프로젝트-구조"></a>
### 🧩 도메인 설계 특징
- **도메인 단위 독립 구성**
  - 기능별(경매, 입찰, 포인트 등)로 명확히 분리
  - 도메인 간 간섭 최소화 → 유지보수와 확장성에 유리
- **CQRS 패턴 적용**
  - Command(쓰기)와 Query(조회)로 서비스 역할을 분리  
  - 조회는 `QueryService`, 변경 작업은 `CommandService`  
  - 책임이 명확해지고, 트래픽 증가 시 확장에도 유리
- **Common 모듈 분리**
  - 인증/보안, 예외 처리, 분산락, 응답 포맷, 유틸리티 등을 별도 계층에서 관리  
  - 프로젝트 전체의 일관성과 재사용성을 확보
<details>
<summary><strong>📁 Directory Structure 펼치기</strong></summary>

```
src
├── main
│   ├── java
│   │   └── org.example.lastcall
│   │        ├── common
│   │        │    ├── config              # 공통 설정 (JPA, Redis, S3, Swagger 등)
│   │        │    ├── entity              # 기본 엔티티(BaseEntity, SoftDelete)
│   │        │    ├── exception           # 글로벌 예외 처리
│   │        │    ├── lock                # 분산락 (Redis 기반)
│   │        │    ├── monitoring          # Health Check, 지표 노출
│   │        │    ├── response            # ApiResponse, PageResponse
│   │        │    ├── security            # JWT 인증/인가
│   │        │    └── util                # 공통 유틸
│   │        │
│   │        └── domain                   # 도메인 단위 조직 (DDD-lite)
│   │             ├── auction             # 경매
│   │             │    └── event          # 경매 Domain Event + RabbitMQ 메시지 처리
│   │             │         ├── producer  # 경매 시작/종료 메시지 발행 (Publisher)
│   │             │         └── consumer  # 경매 메시지 수신 처리 (Listener)
│   │             ├── auth                # 인증/인가
│   │             │    └── email          # 이메일 인증 컴포넌트
│   │             ├── bid                 # 입찰
│   │             ├── point               # 포인트 
│   │             ├── product             # 상품
│   │             └── user                # 사용자
│   │
│   └── resources
│        ├── application.yml
│        ├── application-dev.yml
│        └── application-local.yml
│
└── test
    └── java
         └── org.example.lastcall         # 도메인 단위 테스트 및 Redis 분산락 통합 테스트
```
</details>
