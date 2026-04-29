## 학습 로그 #2

**시간**: 04/29 14:30 ~ 17:30 (약 3시간)
**학습 범위**: 2단계 — DB 연동 (JdbcTemplate, H2)

### 1. 막힌 것의 종류

> Gradle 스코프(runtimeOnly vs implementation) 개념이 낯설었다

### 2. 이번 타임의 학습 전략

- 이전에 바꾸기로 한 전략: 딥다이브 보류, 빠른 PR 목표

> Gradle 스코프(runtimeOnly vs implementation) 개념이 낯설었다
> JdbcTemplate 사용법을 몰랐다
> 테스트 환경에서 DB가 분리되어야 한다는 걸 몰랐다 (@SpringBootTest webEnvironment, 인메모리 DB)

### 2. 이번 타임의 학습 전략

- 이전에 바꾸기로 한 전략: 딥다이브 보류, 빠른 PR 목표
- 실제 학습 과정
    1. 공식문서 중심으로 의존성 파악 — mvnrepository, Spring Boot 레퍼런스, H2 공식문서
    2. Gradle 스코프 개념은 WHY로 이해 후 넘어감 (딥다이브는 옵시디언 메모)
    3. application.properties 설정, schema.sql 자동 실행 흐름 파악
    4. Repository 계층 분리 후 JdbcTemplate으로 CRUD 구현
    5. 테스트 DB 분리 이슈 — 에러 원인 파악 후 인메모리 H2로 해결

### 3. 전략 평가
- **효과적이었던 것**
    - 공식문서를 직접 찾아 읽으니 키워드가 어디서 나왔는지 맥락이 잡혔다
    - 딥다이브 보류 전략이 효과적이었다 — classpath, webEnvironment 등 주제를 메모만 하고 넘어가니 흐름이 끊기지 않았다
    - 에러 메시지를 읽고 원인을 스스로 좁혀가는 경험이 됐다 (DB 잠금, null 주입 등)

- **비효과적이었던 것**
    - 공식문서에서 정확한 키워드를 찾는 데 시간이 걸렸다
    4. Repository 계층 분리 후 JdbcTemplate으로 CRUD 구현
    5. 테스트 DB 분리 이슈 — 에러 원인 파악 후 인메모리 H2로 해결

### 3. 전략 평가
- **효과적이었던 것**
    - 공식문서를 직접 찾아 읽으니 키워드가 어디서 나왔는지 맥락이 잡혔다
    - 딥다이브 보류 전략이 효과적이었다 — classpath, webEnvironment 등 주제를 메모만 하고 넘어가니 흐름이 끊기지 않았다
    - 에러 메시지를 읽고 원인을 스스로 좁혀가는 경험이 됐다 (DB 잠금, null 주입 등)

- **비효과적이었던 것**
    - 공식문서에서 정확한 키워드를 찾는 데 시간이 걸렸다

- **막힌 것의 종류와 전략의 궁합**
    - 코드 사용법 모를 때 공식문서 → 직접 타이핑 흐름이 잘 맞았다
    - 계층 분리 개념은 기존 코드와 비교하니 빠르게 이해됐다

### 4. AI 피드백
- 공식문서 링크를 먼저 받고 직접 찾아 읽는 방식이 검색보다 신뢰도 높은 정보를 얻는 데 효과적이었다

### 5. 다음 타임에 바꿀 것
- 공식문서에서 키워드를 찾는 속도를 높이기 위해 Ctrl+F 활용을 더 적극적으로 한다
- 테스트 환경 설정은 초반에 먼저 잡아두는 습관을 들인다

### 6. 딥다이브 보류 목록
- Java classpath란?
- Gradle 스코프 차이 (implementation vs runtimeOnly 심화)
- SpringBootTest webEnvironment 옵션 차이
