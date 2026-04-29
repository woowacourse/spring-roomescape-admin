# 1단계 요구사항 정리

방탈출 카페 관리자가 전화·현장 예약을 직접 등록·관리하는 상황에 필요한 예약 관리 API를 만든다

## 사용자 요구사항

- 관리자는 예약을 추가할 수 있다.
- 관리자는 예약을 조회할 수 있다.
- 관리자는 예약을 삭제할 수 있다.

## 시스템 요구사항

#### 데이터 저장 및 관리

- 별도의 외부 데이터베이스를 연결하지 않고 In-Memory 상에서 동작하도록 한다.
- 애플리케이션의 메모리는 List, AtomicLong으로 구현한다.
- 예약의 ID는 동시성을 고려하여 AtomicLong을 기반으로 순차적으로 부여한다.
- 데이터는 영속성을 가지지 않고, 서버 재시작시 모두 초기화된다.

#### 통신

- 별도의 프론트엔드는 구현하지 않는다.
- 데이터의 교환 포맷은 JSON을 사용한다.
- HTTP 기반의 REST API를 통해 통신한다.

#### 구현 관점

- 이름, 시간, 날짜를 담을 하나의 Reservation 객체가 필요하다.
- 이름, 시간, 날짜를 각각의 객체로 설정하여 정상 형식인지 검증한다.

#### 요구사항 정리

- 사용자 이름은 최대 10자이다.
- 날짜의 형식은 YYYY-MM-DD 이다.
- 시간은 HH:MM 이다.
- 해당 형식이 맞지 않는 경우 예외를 발생한다.

#### API

예약 조회 GET `/reservations`

- 요청: X
- 응답: `[{id, name, date, time}, ...]`

예약 추가 POST `/reservations`

- 요청: `{name, date, time}`
- 응답: `{id, name, date, time}`

예약 삭제 DELETE `/reservations/{id}`

- 요청: X
- 응답: `200 OK`

#### 더 알아봐야 할 것

- AtomicLong vs Long의 차이
- Reservation에서 AtomicLong을 써야하나?? Long을 써야하나?
- @RestController vs @Controller의 차이
- @RestController = @ResponseBody + @Controller라면 @RestController가 더 적합한 거 아닐까...?
- 테스트 학습을 보면 ResponseEntity<> 를 사용해서 반환했는데, 이것의 사용하는 이유는?
- 정상적인 값인지를 검증을 하기 위해서는 VO가 필요하다고 생각했음. 다만 이것이 1단계에서 너무 복잡한 추상화였고, 2,3,4단계가 남았는데 속도를 저해하는 악영향이 있었는지
- 1단계에서 단순히 조회, 삭제, 생성 세 가지의 기능이 존재했다. 다만 요구사항 명세를 보았을 때 요청/응답의 형태가 달랐으며, 도메인과의 타입이 매칭되지 않았다. (요청 응답을 String으로 원하지만, 난
  VO를 통해 만들었음)
- 사용자 관점에서 생각해보았을 때 앞 뒤 공백이 들어간 입력을 한다거나, 정확한 날짜 형식을 검증할 수 있어야 편리하게 사용할 수 있다는 생각에 VO -> DTO 도입의 이유가 생겼음. 다만 이게 현재 단계에서
  복잡한 추상화인 것 같고, 미션을 4단계까지 진행해야하는데, 단순히 String으로 하고 다음 단계로 넘어가는 것도 좋았을 것 같다.
- 정적 펙토리 메서드의 사용의 근거 = 기존에는 new ReservationResponse()로 해서 인자를 넣어서 해주었지만, 이렇게 하는 경우에는 ReservationResponse의 생성은
  Controller에서 맡게 됨.
- Reservation이 id 값을 가지고 있어도 되는가? DB 종속되게 코드가 작성된 게 아닐까?

# 2단계 요구사항 정리

1단계에서 H2 Database를 붙인다.

#### 요구사항 정리

- 애플리케이션의 List<Reservation>, AtomicLong을 제거한다.
- 1단계에서 만든 조회, 추가, 삭제 API를 JdbcTemplate기반으로 전환한다.
- 예약 추가 시 DB가 생성한 id를 응답에 담는다

#### 더 알아보아야 할 것

- jakson 직렬화와 역직렬화
- 직렬화와 역직렬화 과정에서 JSON 형태와 객체의 형태가 다를 때 jakson에게 원하는 대로 알려주는 방법
- 테스트 하는 과정에서 JdbcTemplate이라는 것을 어떻게
- jdbc:h2:mem:test;DB_CLOSE_DELAY=-1 여기서 DB_CLOSE_DELAY=-1을 어떻게 처리하는가?
- keyholder는 어떻게 동작하는걸까?
- @JsonValue는 뭐지?
- @JasonCreator는 뭐지?
- DriverManagerDataSource 파보기!
