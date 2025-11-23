# LastCall! – 실시간 경매 플랫폼         

🛍️ "이 상품, 내가 꼭 낙찰받고 싶다!"  
하지만 입찰이 어떻게 진행되는지 실시간으로 확인하기 어렵고, 언제 경매가 끝나는지도 불분명한 경우가 많아요.

💸 “입찰했는데 금액이 반영이 안 됐어…”  
입찰 시점 **동시성 문제**나 **데이터 지연**으로 인해 공정한 경쟁이 어려울 때도 있죠.

⏰ “경매 시간을 내가 직접 정할 수 있으면 좋을 텐데…”  
정해진 시간대에만 진행되는 플랫폼은 사용자 입장에서 아쉬움이 많습니다.

그래서 저희는 만들었습니다.  
**“Last Call!"**  *제한된 시간 동안 실시간으로 입찰이 진행되는 온라인 경매 플랫폼*
> 
- "사용자가 직접 경매의 시작/종료 시간을 설정할 수 있어요"
- "입찰은 실시간으로 반영되며, 현재 최고가를 바로 확인할 수 있어요"
- "사용자는 포인트를 충전하고, 보유 포인트 내에서 자유롭게 입찰할 수 있어요."
- "경매가 종료되면 시스템이 자동으로 최고 입찰가를 확인해 낙찰 내역을 기록합니다."

---

## 📌프로젝트 소개 <a id="프로젝트-소개"></a>
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


### ☑️ System Architecture
- API Gateway
  - 인증/인가 처리
  - 도메인별 라우팅
  - 공통 로깅 및 요청 필터링
- 도메인 서비스 (User/Auth, Auction, Bid, Point 등)
  - 각각 독립된 비즈니스 로직 수행
  - 경매·입찰·포인트 등 핵심 기능 처리
- 데이터 저장 계층
  - MySQL: 정합성이 필요한 핵심 데이터 저장
  - Redis: 캐시 + 분산 락 기반 일관성 제어
  - RabbitMQ: 경매 시작/종료 등 비동기 이벤트 처리
  - S3: 이미지 업로드 및 정적 파일 저장

### ⏰ 실시간 입찰 처리 흐름 (Redis 분산 락)
- 입찰 시 Redis 분산 락 적용
- 동시 입찰 시 단 한 명만 성공하도록 동시성 제어 처리
- 포인트 예치/해제 시 동시성 보장

### ✔ 자동 낙찰 처리 (RabbitMQ)
- 경매 시작 시 "종료 예정 메시지" 큐에 예약 발송  
- 종료 시간 도달 → Consumer가 메시지 소비  
- 최고 입찰자 자동 낙찰 처리  

---

## 🔥핵심 기능 <a id="핵심-기능"></a>

### 👤 사용자(User)
- 회원가입 / 로그인(JWT) / 로그아웃
- 이메일 인증
- 프로필 조회 및 수정
- 비밀번호 변경
- JWT Access/Refresh Token 재발급

### 📦 상품(Product)
- 상품 등록 / 수정 / 삭제
- 상품 이미지 등록 / 수정 / 삭제
- 내 상품 전체 조회
- AWS S3 이미지 업로드
- 썸네일 관리

### 🎯  경매(Auction)
- 경매 등록 / 수정 / 삭제
- 경매 조회(전체/상세)
- 내 경매 조회(판매/참여)
- RabbitMQ 기반 자동 시작/종료(낙찰)
- Redis Lock 기반 중복 등록 / 종료 제어
- 자동 시작/종료 시 중복 실행 없이 1회만 처리

### 🏹 입찰(Bid)
- 자동 증가형 입찰(최고 입찰가 + 최소입찰단위)
- 판매자 본인 입찰 불가
- 사용자 재입찰 허용
- Redis Lock 기반 동시성 제어
- 입찰 성공/실패 자동 처리(RabbitMQ)
- 입찰 내역 조회

### 💳 포인트(Point)
- 포인트 충전 및 조회
- `available_point` / `deposit_point` / `settlement_point` 구조
- 입찰 시 포인트 예치(deposit)
- 낙찰 실패 시 자동 해제 (`deposit_point` -> `available_point`)
- 낙찰 성공 시 차감 + 정산 (`deposit_point` -> `settlement_point`)
- 포인트 트랜잭션 정합성 보장 (Redis Lock + DB 트랜잭션)

### 📊 모니터링(Monitoring) 
- Prometheus 기반의 애플리케이션/시스템 메트릭 수집
- Grafana 대시보드로 실시간 시각화
- 주요 성능 지표 모니터링
	- 입찰 요청 대비 입찰 성공률(Bid Success Rate)
	- 분산락 획득 실패율(Lock Failure Rate)
	- 메시지큐 기반 스케줄링의 메시지 처리량(Message Throughput)
	- API 응답 시간 / 오류율 등 서비스 안정성 지표

### ⚙️ 시스템 안정성 기능 
- Redis Lock + DB 트랜잭션 조합으로 중복 처리 방지
- RabbitMQ 이벤트 버전 관리로 오래된 메시지 자동 무시
- 경매 시작·종료 구간 Race Condition 방지

---

## 🗃ERD <a id="erd"></a>
<img width="1527" height="813" alt="image" src="https://github.com/user-attachments/assets/1ac04cee-d4e6-4eed-a0dd-4f64c20bd5c1" />

---

## 📄API 명세서 <a id="api-명세서"></a>

**<b>[🔐Auth]</b>**
| 기능 | Method | URL |
|------|--------|------|
| 이메일 발송 | POST | `/api/v1/email-verifications` |
| 이메일 인증 코드 검증 | POST | `/api/v1/email-verifications/status` |
| 회원가입 | POST | `/api/v1/auth/signup` |
| 로그인 | POST | `/api/v1/auth/login` |
| 로그아웃 | POST | `/api/v1/auth/logout` |
| 회원 탈퇴 | POST | `/api/v1/auth/withdraw` |

<details> <summary><strong>🔐 Auth API 상세</strong></summary>
	
### 📩 이메일 발송 API
#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 인증되지 않은 사용자 요청 가능 (비로그인 OK)

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| email | String | Y | 인증 이메일 주소 |

**요청 예시**
```json
POST /api/v1/email-verifications
Content-Type: application/json

{
  "email": "email@email.com"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success   | Boolean		 | Y | 요청 성공 여부 |
| message   | String 		 | Y | 응답 메시지 |
| data 		| Object \| null | Y | 응답 데이터 |
| timestamp | LocalDateTime	 | Y | 응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br>  <pre>"success": true,<br>  "message": "인증 코드가 발송되었습니다."            ,<br>  "data": null,<br>  "timestamp": "2025-10-26T11:42:29.093758Z"<br>}<br></pre> |
| **400 Bad Request**<br>이메일 형식 오류 | {<br>  <pre>"success": false,<br>  "message": "email: 이메일 형식이 아닙니다.",<br>  "data": null,<br>  "timestamp": "2025-10-26T11:45:31.239147Z"<br>}<br></pre> |
| **409 Conflict**<br>이미 가입/탈퇴한 이메일 | {<br>  <pre>"success": false,<br>  "message": "이미 존재하는 이메일입니다.",<br>  "data": null,<br>  "timestamp": "2025-10-26T11:55:01.503913Z"<br>}<br></pre> |

---

### 🔐 이메일 인증 코드 검증 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 인증되지 않은 사용자 요청 가능 (비로그인 OK)

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| email | String | Y | 인증 이메일 |
| verificationCode | String | Y | 인증 코드 |

**요청 예시**
```json
POST /api/v1/email-verifications/status
Content-Type: application/json

{
  "email": "email@email.com",
  "verificationCode": "875126"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success			   | Boolean | Y |요청 성공 여부 |
| message			   | String | Y |응답 메시지 |
| data				   | Object | Y |응답 데이터 |
| verificationPublicId | String | Y | UUID |
| timestamp			   | LocalDateTime | Y |응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br>  <pre>"success": true,<br>  "message": "이메일 인증이 완료되었습니다.",<br>  "data": { "verificationPublicId": "166cfb21-4718-43f7-a3a1-ca95dbc34f28" },<br>  "timestamp": "2025-10-26T11:42:29.093758Z"<br>}<br></pre> |
| **400 Bad Request**<br>만료된 경우 | {<br>  <pre>"success": false,<br>  "message": "이메일 인증 시간이 만료되었습니다.",<br>  "data": null,<br>  "timestamp": "2025-10-26T11:56:23.213459Z"<br>}</pre> |
| **400 Bad Request**<br>코드 불일치 | {<br>  <pre>"success": false,<br>  "message": "인증 코드가 일치하지 않습니다.",<br>  "data": null,<br>  "timestamp": "2025-10-26T11:57:41.587888Z"<br>}</pre> |
| **400 Bad Request**<br>인증 요청 없음 | {<br>  <pre>"success": false,<br>  "message": "이메일 인증을 먼저 요청해주세요.",<br>  "data": null,<br>  "timestamp": "2025-11-12T08:27:27.787536Z"<br>}</pre> |


---

### 📝 회원가입(Signup) API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 인증되지 않은 사용자 요청 가능 (비로그인 OK)

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| name		  | String | Y | 사용자 이름 |
| nickname 	  | String | Y | 닉네임 |
| email 	  | String | Y | 이메일 |
| password    | String | Y | 비밀번호 |
| addressInfo | Object | Y | 주소 정보 |
| └ address   | String | Y | 주소 |
| └ postcode  | String | Y | 우편번호 |
| └ detailAddress | String | N | 상세주소 |
| phoneNumber | String | Y | 전화번호 |
| userRole    | Enum   | Y | USER |

**요청 예시**
```json
POST /api/v1/auth/signup
Content-Type: application/json

{
  "username": "user",
  "nickname": "user",
  "email": "user@example.com",
  "password": "P@ssw0rd!",
  "addressInfo": {
    "postcode": "06236",
    "address": "서울특별시 강남구 테헤란로 123",
    "detailAddress": "5층 501호"
  },
  "phoneNumber": "010-1234-5678"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success | Boolea | Y | 요청 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object / null | Y | 응답 데이터 |
| timestamp | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **201 Created** | {<br><pre>"success": true,<br>"message": "회원가입이 완료되었습니다.",<br>"data": null,<br>"timestamp": "2025-11-18T21:56:55.334725Z"<br>}</pre> |
| **400 Bad Request**<br>입력값 오류 | {<br><pre>"success": false,<br>"message": "입력값이 올바르지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-18T00:00:00.000000Z"<br>}</pre> |
| **409 Conflict**<br>닉네임 중복 | {<br><pre>"success": false,<br>"message": "이미 사용 중인 닉네임입니다.",<br>"data": null,<br>"timestamp": "2025-11-18T00:00:00.000000Z"<br>}</pre> |
| **400 Bad Request**<br>이메일 인증 UUID 오류 | {<br><pre>"success": false,<br>"message": "이메일 인증 식별자가 유효하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-18T00:00:00.000000Z"<br>}</pre> |


---

### 🔑 로그인(Login) API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 인증되지 않은 사용자 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| email | String | Y | 이메일 |
| password | String | Y | 비밀번호 |

**요청 예시**
```json
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "P@ssw0rd!"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success | Boolean | Y |요청 성공 여부 |
| message | String | Y |응답 메시지 |
| data | Object \| null | Y |응답 데이터 |
| timestamp | LocalDateTime | Y |응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "로그인에 성공했습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T06:31:26.114196Z"</pre>} |
| **401 Unauthorized**<br>이메일 또는 비밀번호 오류 | {<br><pre>"success": false,<br>"message": "이메일 또는 비밀번호가 올바르지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T06:33:59.878401Z"</pre>} |
| **400 Bad Request**<br>입력값 공백 | {<br><pre>"success": false,<br>"message": "이메일 또는 비밀번호가 비어 있습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T13:16:29.296826Z"</pre>} |

---

### 🚪 로그아웃(Logout) API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- `ROLE_USER` 사용자만 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | 로그아웃은 바디 필요 없음 |

**요청 예시**
```json
POST /api/v1/users/logout
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success | Boolean | Y |요청 성공 여부 |
| message | String | Y |응답 메시지 |
| data | Object \| null | Y |응답 데이터 |
| timestamp | LocalDateTime | Y |응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "로그아웃 처리가 완료되었습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T11:07:32.931729Z"</pre>} |
| **401 Unauthorized**<br>비로그인 상태 | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:18:05.618878Z"</pre>} |

---

### ❌ 회원 탈퇴(Withdraw) API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- `ROLE_USER` 사용자만 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| password | String | Y | 현재 비밀번호 |

**요청 예시**
```json
POST /api/v1/users/auth/withdraw
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "password": "current_password"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success | Boolean | Y |요청 성공 여부 |
| message | String | Y |응답 메시지 |
| data | Object \| null | Y |응답 데이터 |
| timestamp | LocalDateTime | Y |응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "회원 탈퇴가 완료되었습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T11:36:16.231210Z"</pre>} |
| **400 Bad Request**<br>비밀번호 불일치 | {<br><pre>"success": false,<br>"message": "비밀번호가 일치하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T11:18:07.394588Z"</pre>} |
| **400 Bad Request**<br>비밀번호 누락 | {<br><pre>"success": false,<br>"message": "회원 탈퇴를 위해 비밀번호를 입력해야 합니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:21:45.909274Z"</pre>} |
| **400 Bad Request**<br>이미 삭제된 계정 | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:10.848179Z"</pre>} |
| **401 Unauthorized**<br>비로그인 접근 | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:18:05.618878Z"</pre>} |

</details>
<br>

**<b>[👤User]</b>**
| 기능 | Method | URL |
|------|--------|-------------------------------|
| 내 정보 조회 | GET | `/api/v1/users/me` |
| 내 정보 수정 | PATCH | `/api/v1/users/me` |
| 비밀번호 변경 | PATCH | `/api/v1/users/me/password` |

<details>
<summary><strong>👤 User API 상세</strong></summary>

---

### 📄 내 정보 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자만 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/users/me
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 | 
|------|------|------------|-------|
| success	   	  | Boolean | Y | 요청 성공 여부 |
| message		  | String | Y | 응답 메세지 |
| data			  | Object | Y | 응답 데이터 |
| └id 			  | Long   | Y |회원 id |
| └username 	  | String | Y |사용자 이름 |
| └nickname 	  | String | Y |사용자 닉네임 |
| └email 		  | String | Y |이메일 |
| └addressInfo 	  | Object | Y |주소 |
| └└address 	  | String | Y |주소 |
| └└postcode	  | String | Y |우편번호 |
| └└detailAddress | String | Y |상세주소 |
| └phoneNumber	  | String | Y |전화번호 |
| └userRole		  | String | Y |사용자 역할 |
| └createdAt	  | LocalDateTime | Y |계정 생성일 |
| └modifiedAt	  | LocalDateTime | Y |계정 정보 수정일 |
| └deletedAt	  | LocalDateTime | Y |계정 삭제일 |
| timestamp	 	  | LocalDateTime | Y |응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내 정보 조회 성공",<br>"data": {<br>&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;"username": "username",<br>&nbsp;&nbsp;"nickname": "nickname",<br>&nbsp;&nbsp;"email": "user@user.com",<br>&nbsp;&nbsp;"addressInfo": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"address": "서울특별시 강남구 테헤란로 1239",<br>&nbsp;&nbsp;&nbsp;&nbsp;"postcode": "06239",<br>&nbsp;&nbsp;&nbsp;&nbsp;"detailAddress": "5층 5019호"<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;"phoneNumber": "010-1234-5678",<br>&nbsp;&nbsp;"userRole": "USER",<br>&nbsp;&nbsp;"createdAt": "2025-10-25T19:22:42.445369",<br>&nbsp;&nbsp;"modifiedAt": "2025-10-26T01:46:20.871735"<br>},<br>"timestamp": "2025-10-25T16:48:44.756031Z"<br></pre>} |
| **401 Unauthorized**<br>비로그인 접근시 | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"</pre>} |

---

### ✏ 내 정보 수정 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자만 요청 가능
  
#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| nickname | String | N | 변경할 닉네임 |
| phoneNumber | String | N | 변경할 전화번호 |
| addressInfo | Object | N | 주소 |
| └address | String | N | 주소 |
| └postcode | String | N | 우편번호 |
| └detailAddress | String | N | 상세주소 |

#### 📌 요청 예시
```json
PATCH /api/v1/users/me
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "nickname": "nickname",
  "addressInfo": {
    "postcode": "06236",
    "address": "서울특별시 강남구 테헤란로 123",
    "detailAddress": "5층 501호"
  },
  "phoneNumber": "010-1234-5678"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 요청 성공 여부 |
| message | String  | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| └id | Long | Y | 회원 id |
| └username | String | Y | 사용자 이름 |
| └nickname | String | Y | 사용자 닉네임 |
| └email | String | Y | 이메일 |
| └addressInfo | Object | Y | 주소 |
| └└address | String | Y | 주소 |
| └└postcode | String | Y | 우편번호 |
| └└detailAddress | String | Y | 상세주소 |
| └phoneNumber | String | Y | 전화번호 |
| └userRole | String | Y | 사용자 역할 |
| └createdAt | LocalDateTime | Y | 계정 생성일 |
| └modifiedAt | LocalDateTime | Y | 계정 정보 수정일 |
| └deletedAt | LocalDateTime | Y | 계정 삭제일 |
| timestamp | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내 정보 수정 성공",<br>"data": {<br>&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;"username": "username",<br>&nbsp;&nbsp;"nickname": "nickname",<br>&nbsp;&nbsp;"email": "user@user.com",<br>&nbsp;&nbsp;"addressInfo": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"address": "서울특별시 강남구 테헤란로 1239",<br>&nbsp;&nbsp;&nbsp;&nbsp;"postcode": "06239",<br>&nbsp;&nbsp;&nbsp;&nbsp;"detailAddress": "5층 5019호"<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;"phoneNumber": "010-1234-5678",<br>&nbsp;&nbsp;"userRole": "USER",<br>&nbsp;&nbsp;"createdAt": "2025-10-26T14:23:10.061495",<br>&nbsp;&nbsp;"modifiedAt": "2025-10-26T14:34:22.354828"<br>},<br>"timestamp": "2025-10-26T05:36:16.825394Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"</pre>} |
| **400 Bad Request**<br>수정할 내용 없음 | {<br><pre>"success": false,<br>"message": "수정할 항목이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T05:47:23.556974Z"</pre>} |
| **400 Bad Request**<br>전화번호 오류 | {<br><pre>"success": false,<br>"message": "phoneNumber: 전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678",<br>"data": null,<br>"timestamp": "2025-10-26T06:00:23.880907Z"</pre>} |

---

### 🔑 비밀번호 변경 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자만 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| oldPassword | String | Y | 현재 비밀번호 |
| newPassword | String | Y | 변경할 비밀번호 |

#### 📌 요청 예시
```json
PATCH /api/v1/users/me/password
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "oldPassword": "OldP@ssw0rd!",
  "newPassword": "NewP@ssw0rd!"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 요청 성공 여부 |
| message | String | Y | 응답 메시지 |
| data | Object \| null | Y | 응답 데이터 |
| timestamp | LocalDateTime | Y | 응답 시간 |


#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "비밀번호가 변경되었습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T06:24:42.752225Z"</pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "로그인후 진행해주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"</pre>} |
| **400 Bad Request**<br>동일한 비밀번호 | {<br><pre>"success": false,<br>"message": "이전 비밀번호와 동일합니다.",<br>"data": null,<br>"timestamp": "2025-10-26T06:26:39.738553Z"</pre>} |

</details><br>

**<b>[💰Point]</b>**
| 기능 | Method | URL |
|------|--------|------|
| 포인트 적립 | POST | `/api/v1/users/points/earn` |
| 포인트 조회 | GET | `/api/v1/users/points` |

<details>
<summary><strong>💰 Point API 상세</strong></summary>

### 💵 포인트 적립 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자 요청 가능

#### 📌 Request Elements
| 필드명 | 타입 | 필수 | 설명 |
|--------|------|------|-------|
| type		  | ENUM	 | Y | 포인트 변동 유형 |
| description | String 	 | Y | 포인트 변동 사유 |
| incomePoint | Long	 | Y | 포인트 금액 |

#### 📌 요청 예시
```json
POST /api/v1/users/points/earn
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "type": "EARN",
  "description": "경매 낙찰 리워드 적립",
  "incomePoint": 5000
}
```

#### 📌 Response Elements
| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| 　ㄴ userId | Long | Y | 사용자 ID |
| 　ㄴ pointId | Long | Y | 사용자의 포인트 ID |
| 　ㄴ availablePoint | Long | Y | 현재 사용 가능한 포인트 |
| 　ㄴ depositPoint | Long | Y | 입찰중인 포인트 |
| 　ㄴ settlementPoint | Long | Y | 정산이 완료된 포인트 |
| timestamp | LocalDateTime | Y | 응답 생성 시간 |


#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **201 CREATED** | {<br><pre>"success": true,<br>"message": "포인트 충전이 완료되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"userId": 2,<br>&nbsp;&nbsp;"pointId": 2,<br>&nbsp;&nbsp;"availablePoint": 365500,<br>&nbsp;&nbsp;"depositPoint": 309600,<br>&nbsp;&nbsp;"settlementPoint": 0<br>},<br>"timestamp": "2025-11-16T15:56:22.645045Z"<br></pre>} |
| **400 BAD REQUEST** | {<br><pre>"success": false,<br>"message": "금액을 입력해 주세요",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |

---

### 💳 포인트 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | Bearer JWT |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자 요청 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/users/points
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드               | 타입            | 필수 여부 | 설명                     |
|--------------------|-----------------|-----------|---------------------------|
| success            | Boolean         | Y         | 응답 성공 여부           |
| message            | String          | Y         | 응답 메시지             |
| data               | Object          | Y         | 응답 데이터             |
| └─ userId          | Long            | Y         | 사용자 ID               |
| └─ pointId         | Long            | Y         | 사용자의 포인트 ID       |
| └─ availablePoint  | Long            | Y         | 현재 사용 가능한 포인트  |
| └─ depositPoint    | Long            | Y         | 입찰중인 포인트         |
| └─ settlementPoint | Long            | Y         | 정산이 완료된 포인트     |
| timestamp          | LocalDateTime   | Y         | 응답 생성 시간          |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "포인트 조회가 완료되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"userId": 2,<br>&nbsp;&nbsp;"pointId": 2,<br>&nbsp;&nbsp;"availablePoint": 365000,<br>&nbsp;&nbsp;"depositPoint": 309600,<br>&nbsp;&nbsp;"settlementPoint": 0<br>},<br>"timestamp": "2025-11-16T15:45:35.882459Z"<br></pre>} |
| **400 BAD_REQUEST**<br>가용 포인트 부족 | {<br><pre>"success": false,<br>"message": "가용 포인트가 부족합니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |
| **400 BAD_REQUEST**<br>예치 포인트 부족 | {<br><pre>"success": false,<br>"message": "예치 포인트가 부족합니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |
| **404 NOT FOUND**<br>포인트 계좌 없음 | {<br><pre>"success": false,<br>"message": "해당 사용자의 포인트 계좌가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |
| **404 NOT FOUND**<br>포인트 기록 없음 | {<br><pre>"success": false,<br>"message": "해당 사용자의 포인트 기록을 찾을 수 없습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |
| **404 NOT FOUND**<br>사용자 없음 | {<br><pre>"success": false,<br>"message": "해당 사용자를 찾을 수 없습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:19:57.263898Z"<br></pre>} |


</details><br>

**<b>[🛒Product]</b>**
| 기능 | Method | URL |
|------|--------|------|
| 상품 등록 | POST | `/api/v1/products` |
| 상품 이미지 등록 | POST | `/api/v1/products/{productId}/images` |
| 내 상품 조회 | GET | `/api/v1/products/{productId}` |
| 상품 대표 이미지 지정 | PATCH | `/api/v1/products/{productId}/images/{imageId}/thumbnail` |
| 상품 수정 | PATCH | `/api/v1/products/{productId}` |
| 상품 삭제 | DELETE | `/api/v1/products/{productId}` |
| 상품 이미지 삭제 | DELETE | `/api/v1/products/{productId}/images/{imageId}` |

<details><summary><strong>🛒 Product API 상세</strong></summary>

### 📩 상품 등록 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | 필수 | JWT 토큰 |
| Content-Type | String | 필수 | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| name | String | Y | 상품 이름 |
| category | Enum | Y | 상품 카테고리 |
| description | String | Y | 상품 설명 |

#### 📌 요청 예시
```json
POST /api/v1/products
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "name": "흰둥이 밥그릇",
  "category": "KITCHEN",
  "description": "중고가격 10만원이 넘는 흰둥이 밥그릇입니다. 특별히 절반 가격에서부터 시작하겠습니다."
}
```

#### 📌 Response Elements
| 필드 | 타입 | 설명 |
|------|------|------|
| success   | Boolean 	| 성공 여부 		|
| message   | String  	| 응답 메시지 		|
| data      | Object  	| 상품 데이터 		|
| ⌙productId| Long  	| 상품 ID		|
| ⌙userId   | Long  	| 사용자 ID 		|
| ⌙name     | Stirng    | 상품명 			|
| ⌙category | ENUM      | 상품 카테고리 	|
| ⌙description | String | 상품 설명 		|
| ⌙createdAt | LocalDateTime | 생성 시간 |
| ⌙modifedAt | LocalDateTime | 수정 시간 |
| timestamp  | LocalDateTime | 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **201 CREATED** | {<br><pre>"success": true,<br>"message": "상품을 등록했습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"userId": 1,<br>&nbsp;&nbsp;"name": "흰둥이 밥그릇",<br>&nbsp;&nbsp;"category": "KITCHEN",<br>&nbsp;&nbsp;"description": "음식 담아서 사진 찍으면 예쁩니다.",<br>&nbsp;&nbsp;"createdAt": "2025-10-17T22:30:53.694844",<br>&nbsp;&nbsp;"modifiedAt": "2025-10-17T22:30:53.694844"<br>},<br>"timestamp": "2025-10-17T13:30:53.7099302"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "상품명은 필수 입력값입니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "상품 카테고리는 필수 입력값입니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "상품 설명은 필수 입력값입니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 접근입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |

---


### 📩 상품 이미지 등록 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |
| Content-Type | multipart/form-data | Y | 이미지 업로드 |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| image | MultipartFile | Y | 이미지 파일 |

#### 📌 요청 예시
```json
POST http://localhost:8080/api/v1/products/1/images/append
Content-Type: multipart/form-data; boundary=boundary

--boundary
Content-Disposition: form-data; name="image"; filename="testImage.png"
Content-Type: image/png

< /Users/user/Desktop/testImage.png

--boundary
Content-Disposition: form-data; name="image"; filename="testImage2.jpeg"
Content-Type: image/jpeg

< /Users/user/Desktop/testImage2.jpeg
--boundary--
```

#### 📌 Response Elements
| 필드 | 타입 | 설명 |
|------|------|------|
| success | Boolean | 요청 성공 여부 |
| message | String  | 응답 메세지 |
| data	  | Object  | 응답 데이터 |
| ⌙id 		 | Long 		 | 이미지 ID 			|
| ⌙productId | Long			 | 상품 ID		    |
| ⌙imageType | Enum 		 | DETAIL/THUMBNAIL |
| ⌙imageUrl  | String		 | S3 URL  			|
| ⌙createdAt | LocalDateTime | 생성 시간		    |
| ⌙modifiedAt| LocalDateTime | 수정 시간 			|
| timestamp  | LocalDateTime | 응답 시간 			|

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **201 CREATED** | {<br><pre>"success": true,<br>"message": "상품 이미지를 추가등록했습니다.",<br>"data": [<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"productId": 3,<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageType": "DETAIL",<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "https://queens-auction.../1762258475848_test",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-11-04T21:14:36.201902",<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2025-11-04T21:14:36.201902"<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"productId": 3,<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageType": "DETAIL",<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "https://queens-auction.../123588643_test",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-11-04T21:14:36.201902",<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2025-11-04T21:14:36.201902"<br>&nbsp;&nbsp;}<br>],<br>"timestamp": "2025-10-17T13:30:53.7099302"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "사진은 최대 10장 첨부할 수 있습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "중복 이미지입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 접근입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |
| **404 Not Found** | {<br><pre>"success": false,<br>"message": "상품이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |

---

### 📩 내 상품 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |
| Content-Type | String | Y | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/products/me
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success 		  | Boolean		  | Y | 요청 성공 여부	 |
| message 		  | String 		  | Y | 응답 메세지 	 |
| data	 		  | Object 		  | Y | 응답 데이터	 |
| ⌙content 		  | Object 		  | Y | 페이지 컨텐츠	 |
| ⌙⌙id 			  | Long		  | Y | 상품 ID		 |
| ⌙⌙name 		  | String 		  | Y | 상품명		 |
| ⌙⌙thumbnailUrl  | String 		  | Y | 대표 이미지 URL |
| ⌙⌙createdAt 	  | LocalDateTime | Y | 생성 시간		 |
| ⌙⌙modifiedAt    | LocalDateTime | Y | 수정 시간		 |
| ⌙totalElements  | Long 	   	  | Y | 결과 수		 |
| ⌙totalPages 	  | Long	 	  | Y | 총 페이지 수	 |
| ⌙number 		  | Long	 	  | Y | 페이지 넘버	 |
| ⌙size 		  | Long	 	  | Y | 페이지 사이즈	 |
| timestamp		  | LocalDateTime | Y | 응답 시간		 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "상품을 전체 조회했습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "흰둥이 밥그릇",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"thumbnailUrl": "https://queens-auction...testImage.png",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-17T22:30:53.694844",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2025-10-17T22:30:53.694844"<br>&nbsp;&nbsp;&nbsp;&nbsp;}, ...<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 1,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 20,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-10-23T02:26:26.077214Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 접근입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |

---

### 📩 상품 대표 이미지 지정 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |
| Content-Type | String | Y | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
PATCH /api/v1/products/{productId}/images/{imageId}
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success	 | Boolean		 | Y | 응답 여부 	 		  |
| message	 | String		 | Y | 응답 메세지 		  |
| data		 | Object		 | Y | 응답 데이터			  |
| ⌙id		 | Long			 | Y | 이미지 ID			  |
| ⌙productId | Long			 | Y | 상품 ID			  |
| ⌙imageType | ENUM			 | Y | THUMBNAIL / DETAIL |
| ⌙imageUrl	 | String		 | Y | 이미지 URL			  |
| ⌙createdAt | LocalDateTime | Y | 생성 시간			  |
| ⌙modifiedAt| LocalDateTime | Y | 수정 시간			  |
| timestamp	 | LocalDateTime | Y | 응답 시간			  |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "대표 이미지 변경에 성공했습니다.",<br>"data": [<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"productId": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageType": "THUMBNAIL",<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "https://queens-auction...testImage.png",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-23T11:26:15.893581",<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2025-10-23T11:26:15.893581"<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"productId": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageType": "DETAIL",<br>&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "https://queens-auction...testImage.png",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-23T11:26:15.898906",<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2025-10-23T11:26:15.898906"<br>&nbsp;&nbsp;}<br>],<br>"timestamp": "2025-10-23T02:26:39.734502Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 접근입니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |
| **403 Forbidden** | {<br><pre>"success": false,<br>"message": "시작되지 않은 경매만 상품을 수정하거나 삭제할 수 있습니다.",<br>"data": null,<br>"timestamp": "2025-10-17T13:30:53.7099302"<br></pre>} |
| **404 Not Found** | {<br><pre>"success": false,<br>"message": "상품이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |

---

### 📩 상품 수정 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰		    |
| Content-Type  | String | Y | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| name 		  | String	 | N | 상품명 |
| category	  | ENUM	 | N | 카테고리 |
| description | String	 | N | 상품 설명 |


#### 📌 요청 예시
```json
PATCH /api/v1/products/{productId}
Authorization: Bearer jwt_token_string
Content-Type: application/json

{
  "name": "수정된 상품명",
  "description": "상품 설명 수정",
  "category": "기타"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success	   | Boolean	   | Y | 응답 성공 여부 |
| message	   | String		   | Y | 응답 메세지 |
| data		   | Object		   | Y | 응답 데이터 |
| ⌙id		   | Long		   | Y | 상품 ID |
| ⌙userId	   | Long		   | Y | 사용자 ID |
| ⌙name		   | String		   | Y | 상품명 |
| ⌙description | String		   | Y | 상품 설명 |
| ⌙category	   | ENUM		   | Y | 카테고리 |
| ⌙createdAt   | LocalDateTime | Y | 생성 시간 |
| ⌙modifiedAt  | LocalDateTime | Y | 수정 시간 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "상품 정보가 수정되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"name": "수정된 상품명",<br>&nbsp;&nbsp;"description": "상품 설명 수정",<br>&nbsp;&nbsp;"category": "기타",<br>&nbsp;&nbsp;"manufacturer": "삼성",<br>&nbsp;&nbsp;"modelNumber": "SM-001",<br>&nbsp;&nbsp;"size": "M",<br>&nbsp;&nbsp;"color": "Black",<br>&nbsp;&nbsp;"condition": "USED",<br>&nbsp;&nbsp;"startPrice": 10000,<br>&nbsp;&nbsp;"createdAt": "2025-10-10T11:25:11.335212",<br>&nbsp;&nbsp;"modifiedAt": "2025-10-11T08:11:31.231232"<br>},<br>"timestamp": "2025-10-11T08:11:31.231232Z"<br></pre>} |
| **400 Bad Request** | {<br><pre>"success": false,<br>"message": "요청값이 올바르지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 사용자입니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |
| **403 Forbidden** | {<br><pre>"success": false,<br>"message": "시작되지 않은 경매만 상품 수정이 가능합니다.",<br>"data": null,<br>"timestamp": "2025-10-12T13:33:22.930291"<br></pre>} |
| **404 Not Found** | {<br><pre>"success": false,<br>"message": "상품이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |

---

### 📩 상품 삭제 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |
| Content-Type | String | Y | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
DELETE /api/v1/products/{productId}
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| deleted | Boolean | Y | 삭제 성공 여부 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "상품이 삭제되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"deleted": true<br>},<br>"timestamp": "2025-10-12T12:30:53.694844Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 사용자입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T15:22:36.640639Z"<br></pre>} |
| **403 Forbidden** | {<br><pre>"success": false,<br>"message": "시작되지 않은 경매만 상품 삭제가 가능합니다.",<br>"data": null,<br>"timestamp": "2025-10-13T13:20:13.132121"<br></pre>} |
| **404 Not Found** | {<br><pre>"success": false,<br>"message": "상품이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-09-21T14:30:45"<br></pre>} |

---

### 📩 상품 이미지 삭제 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |
| Content-Type | String | Y | application/json |

#### 📌 Role Requirement
- 로그인 사용자만 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
DELETE /api/v1/products/{productId}/images/{imageId}
Authorization: Bearer jwt_token_string
Content-Type: application/json
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| deleted | Boolean | Y | 삭제 성공 여부 |

#### 📌 응답 표
| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "상품 이미지가 삭제되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"deleted": true<br>},<br>"timestamp": "2025-10-12T18:11:14.231232Z"<br></pre>} |
| **401 Unauthorized** | {<br><pre>"success": false,<br>"message": "인증되지 않은 사용자입니다.",<br>"data": null,<br>"timestamp": "2025-11-12T11:22:36.640639Z"<br></pre>} |
| **403 Forbidden** | {<br><pre>"success": false,<br>"message": "시작되지 않은 경매만 상품 수정 또는 삭제가 가능합니다.",<br>"data": null,<br>"timestamp": "2025-10-13T13:20:13.132121"<br></pre>} |
| **404 Not Found** | {<br><pre>"success": false,<br>"message": "상품 이미지가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-03-01T14:30:45"<br></pre>} |

</details><br>

**<b>[🎯 Auction]</b>**
| 기능 | Method | URL |
|------|--------|------|
| 경매 등록 | POST | `/api/v1/auctions/{productId}` |
| 경매 전체 조회 | GET | `/api/v1/auctions` |
| 경매 상세 조회 | GET | `/api/v1/auctions/{auctionId}` |
| 경매 수정 | PATCH | `/api/v1/auctions/{auctionId}` |
| 경매 삭제 | DELETE | `/api/v1/auctions/{auctionId}` |
| 경매 입찰 | POST | `/api/v1/auctions/{auctionId}/bids` |
| 경매 마감 처리 | PATCH | `/api/v1/auctions/{auctionId}/close` |
| 경매 재등록 | POST | `/api/v1/auctions/{auctionId}/reopen` |
| 내 경매 조회 | GET | `/api/v1/auctions/me` |

<details> <summary><strong>🎯 Auction API 상세</strong></summary>

### 📦 경매 등록 API (Create Auction)

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- `ROLE_USER` 만 가능  
- 본인이 등록한 상품만 경매 등록 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| startingBid | Long | Y | 경매 시작 가격 |
| bidStep | Long | Y | 경매 입찰 단위 |
| startTime | LocalDateTime | Y | 경매 시작 시간 |
| endTime | LocalDateTime | Y | 경매 종료 시간 |

#### 📌 요청 예시
```json
POST http://localhost:8080/api/v1/auctions/{productId}
Content-Type: application/json

{
  "startingBid": 100,
  "bidStep": 10,
  "startTime": "2025-09-24T10:00:00",
  "endTime": "2025-09-27T10:00:00"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙id | Long | Y | 경매 ID |
| ⌙productId | Long | Y | 상품 ID |
| ⌙userId | Long | Y | 판매자 ID |
| ⌙startingBid | Long | Y | 시작가 |
| ⌙bidStep | Long | Y | 입찰 단위 |
| ⌙startTime | LocalDateTime | Y | 시작 시간 |
| ⌙endTime | LocalDateTime | Y | 종료 시간 |
| ⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙createdAt   | LocalDateTime | Y | 생성 시간 |
| ⌙modifiedAt  | LocalDateTime | Y | 수정 시간 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 예시

| 코드 | 응답(JSON) |
|------|-------------|
| **201 CREATED** | {<br><pre>"success": true,<br>"message": "경매가 등록되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"productId": 1,<br>&nbsp;&nbsp;"userId": 1,<br>&nbsp;&nbsp;"startingBid": 100,<br>&nbsp;&nbsp;"bidStep": 10,<br>&nbsp;&nbsp;"startTime": "2025-09-24T10:00:00",<br>&nbsp;&nbsp;"endTime": "2025-09-27T10:00:00",<br>&nbsp;&nbsp;"status": "SCHEDULED",<br>&nbsp;&nbsp;"createdAt": "2025-09-22T01:25:21.0769685",<br>&nbsp;&nbsp;"modifiedAt": null<br>},<br>"timestamp": "2025-09-23T16:25:21.081975900Z"</pre>} |
| **400 Bad Request**<br>유효성 검증 실패 | {<br><pre>"success": false,<br>"message": "시작일은 현재 시각 이후여야 합니다.",<br>"data": null,<br>"timestamp": "2025-11-15T12:40:02.422371Z"</pre>} |
| **409 Conflict**<br>중복 경매 | {<br><pre>"success": false,<br>"message": "해당 상품의 경매가 이미 존재합니다.",<br>"data": null,<br>"timestamp": "2025-09-23T16:25:21.081975900Z"</pre>} |
| **404 Not Found**<br>상품 없음 | {<br><pre>"success": false,<br>"message": "상품이 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-09-23T16:25:21.081975900Z"</pre>} |
| **403 Forbidden**<br>상품 소유자 아님 | {<br><pre>"success": false,<br>"message": "해당 상품의 소유자가 아닙니다.",<br>"data": null,<br>"timestamp": "2025-09-23T16:25:21.081975900Z"</pre>} |

---

### 📦 경매 전체 조회 API (Read All Auctions)

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| sort | String | N | latest / endTime / participantCount (기본 latest) |
| page | Integer | N | 페이지 번호 (기본 0) |
| size | Integer | N | 페이지 크기 (기본 10) |
| category | Enum | N | FASHION_MEN / FASHION_WOMEN / FASHION_KIDS … |

#### 🔐 Role Requirement
- 모든 사용자 가능 (비로그인 OK)

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|

#### 📌 요청 예시
```json
### 기본 최신순
GET /api/v1/auctions

### 마감 임박순
GET /api/v1/auctions?sort=endTime,asc

### 인기순
GET /api/v1/auctions?sort=participantCount,desc

### 카테고리 필터
GET /api/v1/auctions?category=ACCESSORY
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙content | Object | Y | 페이지 컨텐츠 |
| ⌙⌙id | Long | Y | 경매 ID |
| ⌙⌙imageUrl | String | Y | 대표 이미지 |
| ⌙⌙productName | String | Y | 상품명 |
| ⌙⌙participantCount | Integer | Y | 참여자 수 |
| ⌙totalElements  | Long 	   	  | Y | 결과 수		 |
| ⌙totalPages 	  | Long	 	  | Y | 총 페이지 수	 |
| ⌙size 		  | Long	 	  | Y | 페이지 사이즈 |
| ⌙number 		  | Long	 	  | Y | 페이지 넘버	 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 예시

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK (최신순)** | {<br><pre>"success": true,<br>"message": "경매가 전체 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url2",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "귀걸이",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 15<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url1",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "바지",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 30<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 2,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 10,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-09-26T16:37:06.094067100Z"</pre>} |
| **200 OK (마감임박순)** | {<br><pre>"success": true,<br>"message": "경매가 전체 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url1",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "바지",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 30<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url2",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "귀걸이",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 15<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 2,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 10,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-09-26T16:37:06.094067100Z"</pre>} |
| **200 OK (인기순)** | {<br><pre>"success": true,<br>"message": "경매가 전체 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url1",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "바지",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 30<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url2",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "귀걸이",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 15<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 2,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 10,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-09-26T16:37:06.094067100Z"</pre>} |
| **200 OK (카테고리순)** | {<br><pre>"success": true,<br>"message": "경매가 전체 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image-url2",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "귀걸이",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"participantCount": 1<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 1,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 10,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-09-26T16:37:06.094067100Z"</pre>}  |

---

### 📦 경매 상세 조회 API (Read Auction Detail)

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | N | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함 - 사용자 참여 여부 판단에 사용 |

#### 🔐 Role Requirement
- 모든 사용자 조회 가능  
- 단, **입찰 정보(myParticipated, canBid)는 로그인 사용자만 확인 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/auctions/{auctionId}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 경매 ID |
| message | String | Y | 상품 ID |
| data | Object | Y | 판매자 ID |
| ⌙id | Long | Y | 경매 ID |
| ⌙productId | Long | Y | 상품 ID |
| ⌙imageUrl | String | Y | 대표 이미지 URL |
| productName | String | Y | 상품명 |
| productDescription | String | Y | 상품 설명 |
| startTime | LocalDateTime | Y | 경매 시작 시간 |
| endTime | LocalDateTime | Y | 경매 종료 시간 |
| startingBid | Long | Y | 시작가 |
| bidStep | Long | Y | 입찰 단위 |
| myParticipated | Boolean | Y | 로그인 사용자의 참여 여부 |
| status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| canBid | Boolean | Y | 로그인 여부(입찰 가능 여부) |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "해당 경매가 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;"imageUrl": "https://example.com/images/jacket-detail.jpg",<br>&nbsp;&nbsp;"productName": "빈티지 가죽 재킷",<br>&nbsp;&nbsp;"productDescription": "고급 천연 가죽으로 만든 빈티지 스타일 재킷입니다.",<br>&nbsp;&nbsp;"startTime": "2025-10-20T10:00:00",<br>&nbsp;&nbsp;"endTime": "2025-10-22T10:00:00",<br>&nbsp;&nbsp;"startingBid": 10000,<br>&nbsp;&nbsp;"bidStep": 500,<br>&nbsp;&nbsp;"myParticipated": false,<br>&nbsp;&nbsp;"status": "SCHEDULED",<br>&nbsp;&nbsp;"canBid": false<br>},<br>"timestamp": "2025-10-26T22:47:46.150320300Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-14T18:17:44.832445500Z"</pre>} |

---

### 🛠 내 경매 수정 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |
| Content-Type | String | Y | application/json |

#### 🔐 Role Requirement
- 로그인한 사용자  
- **시작되지 않은 본인의 경매만 수정정 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| startingBid | Long | N | 경매 시작가 |
| bidStep | Long | N | 입찰 단위 |
| startTime | LocalDateTime | N | 경매 시작 시간 |
| endTime | LocalDateTime | N | 경매 종료 시간 |

#### 📌 요청 예시
```json
PATCH /api/v1/my/auctions/{auctionId}
Content-Type: application/json

{
  "startingBid": 20000,
  "bidStep": 1000,
  "startTime": "2025-10-28T15:00:00",
  "endTime": "2025-10-30T22:00:00"
}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙id | Long | Y | 경매 ID |
| ⌙productId | Long | Y | 상품 ID |
| ⌙userId | Long | Y | 유저 ID |
| ⌙startingBid | Long | Y | 경매 시작가 |
| ⌙bidStep | Long | Y | 입찰 단가 |
| ⌙startTime | LocalDateTime | Y | 시작 시간 |
| ⌙endTime | LocalDateTime | Y | 종료 시간 |
| ⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙createdAt   | LocalDateTime | Y | 생성 시간 |
| ⌙modifiedAt  | LocalDateTime | Y | 수정 시간 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내 경매가 수정되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"auctionId": 12,<br>&nbsp;&nbsp;"productId": 10,<br>&nbsp;&nbsp;"userId": 2,<br>&nbsp;&nbsp;"startingBid": 20000,<br>&nbsp;&nbsp;"bidStep": 1000,<br>&nbsp;&nbsp;"startTime": "2025-10-28T15:00:00",<br>&nbsp;&nbsp;"endTime": "2025-10-30T22:00:00",<br>&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;"updatedAt": "2025-10-27T07:46:20.804301"<br>},<br>"timestamp": "2025-10-26T22:51:23.085760800Z"</pre>} |
| **400 BAD REQUEST** | {<br><pre>"success": false,<br>"message": "시작일은 현재 시각 이후여야 합니다.",<br>"data": null,<br>"timestamp": "2025-11-15T12:40:02.422371Z"</pre>} |
| **403 FORBIDDEN**<br>소유자 아님 | {<br><pre>"success": false,<br>"message": "해당 상품의 소유자가 아닙니다.",<br>"data": null,<br>"timestamp": "2025-10-26T22:51:23.085760800Z"</pre>} |
| **403 FORBIDDEN**<br>진행/종료 경매 수정 불가 | {<br><pre>"success": false,<br>"message": "진행 중이거나 이미 종료된 경매는 수정할 수 없습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T22:51:23.085760800Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T22:51:23.085760800Z"</pre>} |

---

### 🗑 내 경매 삭제 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |

#### 🔐 Role Requirement
- 로그인한 사용자  
- **시작되지 않은 본인의 경매만 삭제 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
DELETE /api/v1/my/auctions/{auctionId}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| 없음 | - | - | - |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내 경매가 삭제되었습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T23:04:45.254085100Z"</pre>} |
| **403 FORBIDDEN**<br>시작된 경매 | {<br><pre>"success": false,<br>"message": "시작되지 않은 경매만 수정하거나 삭제할 수 있습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T23:04:45.254085100Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T23:04:45.254085100Z"</pre>} |

---

### 📦 내 판매 경매 목록 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |

#### 🔐 Role Requirement
- 로그인한 사용자  
- 본인이 판매자로 등록한 경매만 확인 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|

#### 📌 요청 예시
```json
GET /api/v1/my/auctions/selling
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙content | Object | Y | 페이지 컨텐츠 |
| ⌙⌙id | Long | Y | 경매 ID |
| ⌙⌙imageUrl | String | Y | 상품 대표 이미지 |
| ⌙⌙productName | String | Y | 상품명 |
| ⌙⌙productDescription | String | Y | 상품 설명 |
| ⌙⌙currentBid | Long | Y | 현재 최고 입찰가 |
| ⌙⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙⌙startTime | LocalDateTime | Y | 시작 시간 |
| ⌙⌙endTime | LocalDateTime | Y | 종료 시간 |
| ⌙totalElements  | Long 	   	  | Y | 결과 수		 |
| ⌙totalPages 	  | Long	 	  | Y | 총 페이지 수	 |
| ⌙size 		  | Long	 	  | Y | 페이지 사이즈 |
| ⌙number 		  | Long	 	  | Y | 페이지 넘버	 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내 판매한 경매 목록이 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 7,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image3.jpg",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "상품6",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productDescription": "상품 6 판매합니다.",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"currentBid": 10000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"status": "SCHEDULED",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"startTime": "2025-10-27T10:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"endTime": "2025-10-28T10:00:00"<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 5,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image3.jpg",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "상품7",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productDescription": "상품 7 판매합니다.",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"currentBid": 20000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"startTime": "2025-10-24T15:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"endTime": "2025-10-27T22:00:00"<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 2,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 2,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-10-26T23:11:30.856725500Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "조회 가능한 경매가 없습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T23:11:30.856725500Z"</pre>} |

---

### 📦 내가 판매한 경매 단건 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |

#### 🔐 Role Requirement
- 로그인한 사용자  
- **본인이 판매한 경매만 조회 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/my/auctions/selling/{auctionId}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙id | Long | Y | 경매 ID |
| ⌙imageUrl | String | Y | 대표 이미지 URL |
| ⌙productName | String | Y | 상품명 |
| ⌙productDescription | String | Y | 상품 설명 |
| ⌙currentBid | Long | Y | 현재 최고 입찰가 |
| ⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙startTime | LocalDateTime | Y | 경매 시작 시간 |
| ⌙endTime | LocalDateTime | Y | 경매 종료 시간 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내가 판매한 경매 중 해당 경매가 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 4,<br>&nbsp;&nbsp;"imageUrl": "image3.jpg",<br>&nbsp;&nbsp;"productName": "상품5",<br>&nbsp;&nbsp;"productDescription": "상품5 설명입니다.",<br>&nbsp;&nbsp;"currentBid": 20000,<br>&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;"startTime": "2025-10-24T15:00:00",<br>&nbsp;&nbsp;"endTime": "2025-10-27T22:00:00"<br>},<br>"timestamp": "2025-10-26T23:19:57.880244300Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-15T15:07:20.220482400Z"</pre>} |

---

### 📦 내가 참여한 경매 목록 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |

#### 🔐 Role Requirement
- 로그인한 사용자  
- **본인이 참여한 경매만 조회 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|

#### 📌 요청 예시
```json
GET /api/v1/my/auctions/participated
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙content | Object | Y | 페이지 컨텐츠 |
| ⌙⌙id | Long | Y | 경매 ID |
| ⌙⌙imageUrl | String | Y | 상품 대표 이미지 URL |
| ⌙⌙productName | String | Y | 상품명 |
| ⌙⌙productDescription | String | Y | 상품 설명 |
| ⌙⌙currentBid | Long | Y | 현재 최고 입찰가 |
| ⌙⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙⌙startTime | LocalDateTime | Y | 경매 시작 시간 |
| ⌙⌙endTime | LocalDateTime | Y | 경매 종료 시간 |
| ⌙⌙isLeading | Boolean | Y | 최고 입찰 여부 / 낙찰 여부 |
| ⌙⌙myBidAmount | Long | N | 나의 최고 입찰가 (참여 전체 조회 시 null) |
| ⌙totalElements  | Long 	   	  | Y | 결과 수		 |
| ⌙totalPages 	  | Long	 	  | Y | 총 페이지 수	 |
| ⌙size 		  | Long	 	  | Y | 페이지 사이즈 |
| ⌙number 		  | Long	 	  | Y | 페이지 넘버	 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내가 참여한 경매 목록이 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image1.jpg",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "상품1",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productDescription": "상품1 설명입니다.",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"currentBid": 13500,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"startTime": "2025-10-26T10:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"endTime": "2025-10-27T10:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"isLeading": false,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"myBidAmount": 12000<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 4,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imageUrl": "image4.jpg",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productName": "상품4",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"productDescription": "상품4 설명입니다.",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"currentBid": 13500,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"startTime": "2025-10-25T10:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"endTime": "2025-10-29T10:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"isLeading": true,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"myBidAmount": 13500<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 2,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 2,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-10-26T23:29:40.729429800Z"</pre>} |

---

### 📦 내가 참여한 경매 단건 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Cookie | String | Y | HttpOnly 쿠키로 전달되는 access_token, refresh_token 자동 포함  |

#### 🔐 Role Requirement
- 로그인한 사용자  
- **본인이 참여한 경매만 조회 가능**

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|

#### 📌 요청 예시
```json
GET /api/v1/my/auctions/participated/{auctionId}
```

#### 📌 Response Elements
| 필드 | 타입 | 필수 여부 | 설명 |
|------|------|------------|-------|
| success | Boolean | Y | 응답 성공 여부 |
| message | String | Y | 응답 메세지 |
| data | Object | Y | 응답 데이터 |
| ⌙id | Long | Y | 경매 ID |
| ⌙imageUrl | String | Y | 상품 대표 이미지 URL |
| ⌙productName | String | Y | 상품명 |
| ⌙productDescription | String | Y | 상품 설명 |
| ⌙currentBid | Long | Y | 현재 최고 입찰가 |
| ⌙status | Enum | Y | 경매 상태 SCHEDULED / ONGOING / CLOSED / CLOSED_FAILED / DELETE |
| ⌙startTime | LocalDateTime | Y | 경매 시작 시간 |
| ⌙endTime | LocalDateTime | Y | 경매 종료 시간 |
| ⌙isLeading | Boolean | Y | 최고 입찰 여부 / 낙찰 여부 |
| ⌙myBidAmount | Long | Y | 내가 해당 경매에 넣은 최고 입찰가 |
| timestamp	   | LocalDateTime | Y | 응답 시간 |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "내가 참여한 경매 중 해당 경매가 조회되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"imageUrl": "image1.jpg",<br>&nbsp;&nbsp;"productName": "상품1",<br>&nbsp;&nbsp;"productDescription": "상품1 설명입니다.",<br>&nbsp;&nbsp;"currentBid": 13500,<br>&nbsp;&nbsp;"status": "ONGOING",<br>&nbsp;&nbsp;"startTime": "2025-10-26T10:00:00",<br>&nbsp;&nbsp;"endTime": "2025-10-27T10:00:00",<br>&nbsp;&nbsp;"isLeading": false,<br>&nbsp;&nbsp;"myBidAmount": 12000<br>},<br>"timestamp": "2025-10-26T23:22:38.842980900Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-11-15T15:07:20.220482400Z"</pre>} |

</details><br>

**<b>[🏹Bid]</b>**

| 기능 | Method | URL |
|------|--------|------|
| 입찰 등록 | POST | `/api/v1/auctions/{auctionId}/bids` |
| 경매별 전체 입찰 내역 조회 | GET | `/api/v1/auctions/{auctionId}/bids` |

<details><summary><strong>🏹 Bid API 상세</strong></summary>

### 🏹 경매 입찰 생성 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| Authorization | String | Y | JWT 토큰 |

#### 🔐 Role Requirement
- role이 **ROLE_USER**인 사용자만 수행 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| nextBidAmount | Long | Y | 입찰 예정 금액 |

#### 📌 요청 예시
```json
POST /api/v1/auctions/1/bids
Authorization: Bearer jwt_token_string
{
	"nextBidAmount" : "7700"
}
```

#### 📌 Response Elements
| 필드        | 타입           | 필수 여부 | 설명                     |
|-------------|----------------|-----------|--------------------------|
| success     | Boolean        | Y         | 응답 성공 여부           |
| message     | String         | Y         | 응답 메시지              |
| data        | Object         | Y         | 응답 데이터              |
| └ id        | Long           | Y         | 입찰 아이디               |
| └ auctionId | Long           | Y         | 경매 아이디               |
| └ userId    | Long           | Y         | 입찰한 사용자 ID          |
| └ bidAmount | Long           | Y         | 입찰가                    |
| └ createdAt | LocalDateTime  | Y         | 입찰이 등록된 시간        |
| timestamp   | LocalDateTime  | Y         | 응답 생성 시간            |

#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **201 CREATED** | {<br><pre>"success": true,<br>"message": "입찰이 완료되었습니다.",<br>"data": {<br>&nbsp;&nbsp;"id": 27518,<br>&nbsp;&nbsp;"auctionId": 21,<br>&nbsp;&nbsp;"userId": 2,<br>&nbsp;&nbsp;"bidAmount": 7700,<br>&nbsp;&nbsp;"createdAt": "2025-11-13T09:33:13.2969125"<br>},<br>"timestamp": "2025-11-13T00:33:13.396734900Z"</pre>} |
| **400 BAD REQUEST**<br>가용 포인트 부족 | {<br><pre>"success": false,<br>"message": "가용 포인트가 부족합니다.",<br>"data": null,<br>"timestamp": "2025-11-13T00:36:18.599001600Z"</pre>} |
| **403 FORBIDDEN**<br>판매자가 입찰 시도 | {<br><pre>"success": false,<br>"message": "판매자는 자신의 경매에 입찰할 수 없습니다.",<br>"data": null,<br>"timestamp": "2025-10-27T14:33:00.101Z"</pre>} |
| **403 FORBIDDEN**<br>경매 미진행 | {<br><pre>"success": false,<br>"message": "진행 중인 경매일 경우에만 입찰이 가능합니다.",<br>"data": null,<br>"timestamp": "2025-11-17T02:57:29.664169700Z"</pre>} |
| **404 NOT FOUND**<br>경매 없음 | {<br><pre>"success": false,<br>"message": "판매자는 자신의 경매에 입찰할 수 없습니다.",<br>"data": null,<br>"timestamp": "2025-11-13T00:37:09.177044100Z"</pre>} |
| **404 NOT FOUND**<br>포인트 계좌 없음 | {<br><pre>"success": false,<br>"message": "해당 사용자의 포인트 계좌가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T15:37:09.952405743Z"</pre>} |
| **500 SERVER ERROR** | {<br><pre>"success": false,<br>"message": "예상치 못한 서버 오류가 발생했습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T16:22:28.979585700Z"</pre>} |

---

### 🏹 경매 입찰 내역 조회 API

#### 📌 Request Header
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 🔐 Role Requirement
- **모든 사용자** 수행 가능

#### 📌 Request Elements
| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| 없음 | - | - | - |

#### 📌 요청 예시
```json
GET /api/v1/auctions/1/bids
```

#### 📌 Response Elements
| 필드            | 타입           | 필수 여부 | 설명               |
|-----------------|----------------|-----------|--------------------|
| success         | Boolean        | Y         | 요청 성공 여부     |
| message         | String         | Y         | 응답 메시지        |
| data            | Object         | Y         | 응답 데이터        |
| └ content         | Object         | Y         | 페이지 컨텐츠       |
| └  id            | Long           | Y         | 입찰 아이디         |
| └  auctionId     | Long           | Y         | 경매 아이디         |
| └  userId        | Long           | Y         | 유저 아이디         |
| └  bidAmount     | Long           | Y         | 입찰가              |
| └  createdAt     | LocalDateTime  | Y         | 입찰 생성일         |
| └ totalElements | Long           | Y         | 결과 수             |
| └ totalPages    | Long           | Y         | 총 페이지 수        |
| └ size          | Long           | Y         | 페이지 사이즈       |
| └ number        | Long           | Y         | 페이지 넘버         |
| timestamp       | LocalDateTime  | Y         | 응답 생성 시간      |


#### 📌 응답 표

| 코드 | 응답(JSON) |
|------|-------------|
| **200 OK** | {<br><pre>"success": true,<br>"message": "해당 경매의 입찰 내역을 조회합니다.",<br>"data": {<br>&nbsp;&nbsp;"content": [<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 4,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"auctionId": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"nickname": "나라규",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"bidAmount": 130000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-23T16:19:21.960882"<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"auctionId": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"nickname": "나라규",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"bidAmount": 120000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-23T16:17:38.120861"<br>&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"auctionId": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"nickname": "나라규",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"bidAmount": 110000,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2025-10-23T00:09:51.517815"<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;],<br>&nbsp;&nbsp;"totalElements": 3,<br>&nbsp;&nbsp;"totalPages": 1,<br>&nbsp;&nbsp;"size": 5,<br>&nbsp;&nbsp;"number": 0<br>},<br>"timestamp": "2025-10-25T06:11:20.082388500Z"</pre>} |
| **404 NOT FOUND** | {<br><pre>"success": false,<br>"message": "해당 경매가 존재하지 않습니다.",<br>"data": null,<br>"timestamp": "2025-10-26T15:37:09.952405743Z"</pre>} |
| **500 SERVER ERROR** | {<br><pre>"success": false,<br>"message": "예상치 못한 서버 오류가 발생했습니다.",<br>"data": null,<br>"timestamp": "2025-10-25T06:10:08.238270900Z"</pre>} |
</details>

---

## 🖼️와이어 프레임 <a id="와이어-프레임"></a>
https://www.figma.com/design/MXc4uESnjz8e0S8crq0Qi1/%EA%B2%BD%EB%A7%A4?node-id=0-1&p=f&m=draw
<img width="11568" height="5055" alt="image" src="https://github.com/user-attachments/assets/50580245-4504-470f-8262-a76cfe6b0f68" />

---

## ☁️인프라 아키텍처 <a id="인프라-아키텍처"></a>
<img width="991" height="747" alt="Image" src="https://github.com/user-attachments/assets/ada70ad3-8bbe-4874-9705-756161d1e367" />

---

## 🧪테스트 <a id="테스트"></a>
### 서비스 단위 테스트
- 각 도메인 별 서비스 단위 테스트 작성 후 검증
   - 기준: Jacoco 커버리지 60% 이상
   - Mockito 기반 Mock 객체  
   - Redis/RabbitMQ 실제 의존성 제거
     
### 통합 테스트(분산락)
- 분산락 적용한 Point, Bid, Auction 서비스는 통합 테스트로 추가 검증
   - 기준: Jacoco 커버리지 60% 이상
 
### 주요 테스트 파일
<img width="746" height="354" alt="스크린샷 2025-11-18 오후 2 33 32" src="https://github.com/user-attachments/assets/01c3b0cf-5048-4f22-91f2-cafd239e312d" />

[단위 테스트]
- `AuctionCommandServiceTest`
- `AuctionQueryServiceTest`
- `AuthCommandServiceTest`
- `AuthValidatorServiceTest`
- `BidCommandServiceTest`
- `BidQueryServiceTest`
- `PointQueryServiceTest`
- `ProductCommandServiceTest`
- `ProductQueryServiceTest`
- `ProductImageServiceTest`
- `ProductVaildatorServiceTest`
- `UserCommandServiceTest`
- `UserQueryServiceTest`

[통합 테스트]
- `AuctionCommandServiceIntegrationTest`
- `BidCommandServiceIntegrationTest`
- `PointCommandServiceIntegrationTest`

---

## ⚙️성능 테스트 <a id="성능-테스트"></a>
## 🔧Index 성능 개선
<b>1. 부하테스트 시나리오</b>
- 대상API : `GET /api/v1/auctions/me/selling`
- 인덱스 구성 : `(user_id, deleted, created_at DESC)`
- 환경 : nGrinder Controller 1대 / Agent 1대 / Spring Boot 서버 (9090)
- 목적 : 로그인한 사용자가 `내가 판매한 경매 목록 조회` API 호출 시, 인덱스 적용 전/후의 성능 차이를 측정 및 분석
- 적용 이유 : 데이터가 많아질 경우 테이블 풀 스캔이 빈번하게 발생할 것으로 예상됨<br>

<b>2. 결과</b>
<img width="1201" height="433" alt="image" src="https://github.com/user-attachments/assets/fd4e2f11-8827-4765-8628-2ade851ad43a" />
<img width="532" height="149" alt="image" src="https://github.com/user-attachments/assets/5bdb58dc-b0c5-4b49-bd42-f051032b8bc2" /><br>

<b>3. 해석</b><br>

**(적용 전)**  
- TPS(초당 처리량)가 100~350TPS 사이에서 지속적으로 변동
- 일부 구간에서는 150TPS 이하로 급락하는 불안정한 패턴을 보임
- user_id, deleted, created_at 조건에 대한 풀 스캔으로 인한 결과로 분석됨<br>

**(적용 후)**  
- TPS가 200~400TPS 범위에서 비교적 안정적으로 유지
- 평균 처리량은 227.9TPS → 334.4TPS (약 +46%) 로 향상됨
- 응답 시간 편차 줄어들어 일관성 있고 예측 가능한 성능 확보

<br>

## 🔧Redis Cache 성능 개선
<b>1. 부하테스트 시나리오</b>
- 대상API : `GET /api/v1/users/points`
- 환경 : nGrinder Controller 1대 / Agent 1대 / Spring Boot 서버 (9090)
- 목적 : 로그인한 사용자가 `내 포인트 조회` API 호출 시, Redis Cache 적용 전/후의 성능 차이를 측정 및 분석
- 적용 이유 : 포인트 조회는 조회 빈도는 높지만 데이터 변경은 비교적 적은 정적 요청임<br>
- 캐시 TTL : 1시간
   	- 짧은 TTL로 인한 재적재 부하 방지
   	- 충분한 긴 캐시 유지로 Redis hit 비율 높임
   	- 포인트 변경 시 캐시 무효화(@CacheEvict)로 최신성 보장<br>

<b>2. 결과</b>
<img width="2400" height="866" alt="image" src="https://github.com/user-attachments/assets/10d70b67-7ad5-4a1b-86d8-bc6f1981d30d" />
<img width="533" height="148" alt="image" src="https://github.com/user-attachments/assets/5c9bffc2-b5d2-47e5-b0f1-f2fa5abdfe04" /><br>

<b>3. 해석</b>
<br>

**(적용 전)**  
- TPS 불안정하고 주기적으로 하락 구간 반복
- 평균 응답시간(MTT) 역시 변동 폭 크게 나타났으며, 특정 구간에서 응답시간 일시적 증가 및 TPS 감소 패턴 관찰
- DB 조회 요청 집중되면서 I/O 대기 등으로 인한 병목 현상 발생된 것으로 분석됨<br>

**(적용 후)**  
- TPS와 응답 속도 일정 유지
- DB 부하 감소 및 응답 일관성 확보
- TPS 약 31.7% 향상
- 평균 응답 시간 약 2.8배 단축
---

## 📂프로젝트 구조 <a id="프로젝트-구조"></a>
본 프로젝트는 도메인 중심의 멀티 모듈 구조로 설계되어 있으며, User/Auth, Auction, Product, Bid, Point 등 각 도메인을 독립 모듈로 분리하여 기능 간 결합도를 낮추고 유지보수성과 확장성을 강화했습니다.
또한 인증/보안, 공통 유틸리티, 예외 처리와 같은 공통 기능은 별도 계층으로 분리해 전체 서비스의 일관성과 재사용성을 확보했습니다.

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
