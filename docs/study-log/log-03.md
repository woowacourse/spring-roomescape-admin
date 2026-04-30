## 학습 로그 #3

**시간**: 04/30 02:30 ~ 03:00 (약 30분)
**학습 범위**: 3단계 시간관리 

### 1. 막힌 것의 종류
이번에 막힌 것은 어떤 종류의 어려움이었는가? (해당하는 것에 체크)
- [ ] 개념 자체를 모르겠다 (예: "스프링 빈이 뭔지 모르겠다")
- [ ] 개념은 알겠는데 코드로 어떻게 쓰는지 모르겠다 (예: "JdbcTemplate 문법을 모르겠다")
- [ ] 코드는 돌아가는데 이게 맞는 건지 모르겠다 (예: "계층 분리를 이렇게 해도 되나?")
- [x] 기타: JDBC의 타입 변환 원리, DB 스키마는 VARCHAR인데 어떻게 Java의 LocalTime으로 매핑되는지 그 내부 메커니즘이 궁금함.

### 2. 이번 타임의 학습 전략
- 이전에 바꾸기로 한 전략은 무엇이었고, 실행했는가?
  - 단순하게 구현한 뒤, 구현한 내용 중 블랙박스 처럼 남아있는 지점을 '기초 지식 테스트'로 테스트를 진행함.
- 실제로 어떻게 학습했는지 디테일한 과정을 써보세요.
  1. 단순하게 구현하기: ReservationTime 추가 및 Reservation과의 FK 연관 관계 구현
  2. 구현 내용에서 기초 지식 테스트 받기: JdbcTemplate이 어떻게 LocalTime으로 바꿀까?
     * 바뀌는 과정에서 발생하는 타입 변환에 의문을 가짐.
  3. 가설 기반 질문: spring framework가 처리해주고 있을 것이다.
     * `java.sql.ResultSet` 객체를 뜯어보고 interface라서 구현체가 어딘가 있겠다는 생각으로 java.sql library root를 탐색했다.
     * 하지만 java.sql 라이브러리에는 하나의 프로토콜처럼 interface만 존재했다.
     * 다시 Controller로 돌아와서 JdbcTemplate의 queryMethod를 뜯어보고, `org.springframework.jdbc` 내부 구현체를 살펴봤다.
     * 하지만 여전히 ResultSet을 구현하는 객체는 찾지 못했고, "추상화로 인해 외부 설정에서 어떻게 관리하고 있지 않을까?" 라는 추측을 했다.
     * 이 추측을 기반으로 20분이 지나서 spring framework가 처리해주고 있을 것이라는 가설을 세웠다.
  4. 장난감 모델 검증 및 피드백: 관련 클래스 정보를 확인하기 위한 작은 테스트
     * AI 답변이 모호하지 않았기 때문에 `자원 확장 및 심화`를 건너뛴다.
     * AI의 "더 낮은 계층(Driver)이 존재한다"는 힌트로 org.h2.jdbc.JdbcResultSet을 발견함
     * 그래서 정말 JdbcResultSet이 구현체인지 확인하는 학습 테스트 코드를 작성해봤다.
     ```java
      @Test
      void ResultSet_심플_학습_테스트() {
          jdbcTemplate.query("SELECT 1", (rs, rowNum) -> {
              System.out.println("구체 클래스 : " + rs.getClass().getName());
              return null;
          });
      }
      ```
     * 확인 결과: `com.zaxxer.hikari.pool.HikariProxyResultSet`
     * HikariProxyResultSet 관련해서 더 이상 찾지 못했다. 이제부터는 진짜 Spring 설정인 것 같다. 다시 1단계로 돌아가서 반복한다.
  5. 가설 기반 재질문: spring framework가 HikariCP를 Proxy로 H2 Driver에서 구현한 ResultSet을 감싸서 전달한다.
  6. 의도적 파괴 및 실패 기록: 마지막으로 장난감 모델 검증으로 마무리한다.
     * 더이상 진행하기에 너무 방대한 양이므로 미션이 끝난 후 추가 학습 포인트로 남겨둔다.
    ```java
    @Test
    void ResultSet_Proxy_확인_학습_테스트() {
    jdbcTemplate.query("SELECT 1", rs -> {
    // 1. Hikari Pool 겉모습 확인
    System.out.println("ResultSet 겉모습: " + rs.getClass().getName());
    
            // 2. unwrap을 통해 내부의 진짜 'H2' 객체 추출
            ResultSet realH2ResultSet = rs.unwrap(ResultSet.class);
            System.out.println("ResultSet Real 구체: " + realH2ResultSet.getClass().getName());
        });
    }
    ```

### 3. 전략 평가
- 효과적이었던 것과 그 이유
  - 블랙박스 확인 테스트, 무의식적으로 외운 방법들의 허점을 짚을 수 있었다.
- 비효과적이었던 것과 그 이유
  - 내 태도, ResultSet에만 한 시간을 넘게 빼앗겼다. 적절한 학습 시간 분배가 중요해보인다. 3단계는 시간관리니까...
- 막힌 것의 종류(1번)와 전략의 궁합은 어땠는가?
  - 학습법을 통해서 부족한 점을 알게됐으니까 좋았다.

### 4. AI 피드백
- 자신의 학습 전략에 대해 AI 학습 전문가에게 피드백을 요청하고,
  유용했던 제안 1가지 이상 기록

> "블랙박스 탐구 시 'Time-Box' 전략을 도입하라."  
> 특정 내부 동작(예: JDBC 변환 원리)이 궁금할 때, 무작정 파고들기보다 '최대 30분'처럼 시간을 정해두고 탐구하는 거야. 만약 시간이 초과되면 이번 이프처럼 '추가 학습 포인트(Backlog)'로 남겨두고 미션의 메인 흐름(기능 구현)으로 복귀하는 습관을 들이면, 학습의 깊이와 미션의 속도를 동시에 잡을 수 있단다.

### 5. 다음 타임에 바꿀 것
- 유지할 것과 그 이유
  - 모두. 학습에 도움이 됨.
- 바꿀 것과 그 이유
  - 각 단계에서 Time-Box 전략 도입, 한 단계 당 최대 20분을 넘지 않는다, 시간 관리 때문
