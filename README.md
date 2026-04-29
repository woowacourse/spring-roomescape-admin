# spring-roomescape-admin

## 프로그래밍 요구 사항

- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 기본적으로 Java Style Guide을 원칙으로 한다.
- 단계 2·3에서는 레벨1에서 학습했던 JUnit만 활용한 단위 테스트를 작성한다
    - 새로운 테스트 도구나 기법(Spring Boot Test, Mock, RestAssured 등)을 도입하지 않는다
    - 요구사항 테스트에서 RestAssured가 주어진 경우 그대로 사용하되, 그 위에 새 테스트 기법을 쌓지 않는다

## 🚀 1단계: 웹 요청-응답

### 요구사항

방탈출 카페 관리자가 전화·현장 예약을 직접 등록·관리하는 상황에 필요한 예약 관리 API를 만든다.
별도의 데이터베이스 없이 **메모리(List + AtomicLong)** 로 예약 상태를 관리한다.
서버를 재시작하면 데이터는 모두 사라진다.

⚠️ 화면 없음.
브라우저 화면은 만들지 않는다.
API 동작 확인 방법(테스트, HTTP 클라이언트 등)은 스스로 찾는다.

### 예약 CRUD API

| 기능    | 메서드 / URL                 | 요청 본문              | 응답                            |
|-------|---------------------------|--------------------|-------------------------------|
| 예약 조회 | GET /reservations         | —                  | [{id, name, date, time}, ...] |
| 예약 추가 | POST /reservations        | {name, date, time} | {id, name, date, time}        |
| 예약 삭제 | DELETE /reservations/{id} | —                  | 200 OK                        |

### 예약 추가 요청·응답 예시

```
POST /reservations HTTP/1.1
Content-Type: application/json

{
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

```
HTTP/1.1 200
Content-Type: application/json

{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

### 요구사항 테스트

아래 테스트가 모두 통과하면 단계 1 완료.

```
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    void 예약_조회() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 아직 생성 요청이 없으니 0개
    }

    @Test
    void 예약_추가_및_삭제() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

}
```

## 🚀 2단계: 데이터베이스 연동

### 요구사항

1단계 메모리 저장은 서버 재시작 시 예약 데이터가 모두 사라지는 상황이다.
이를 해결하기 위해 예약 CRUD를 H2 데이터베이스로 전환한다.

⚠️ 새로운 테스트 도구나 기법(Spring Boot Test, Mock, RestAssured 추가 활용 등)을 도입하지 않고 레벨1에서 학습했던 JUnit만 활용한 단위 테스트에 집중한다.
요구사항에서 RestAssured가 주어진 경우 그대로 사용하되, 그 위에 새 테스트 기법을 쌓지 않는다.

### 환경 설정

- `build.gradle`에 의존성 추가: `spring-boot-starter-jdbc`, `h2`
- `application.properties`에 H2 콘솔 활성화와 datasource URL을 설정한다:

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:database
```

### 테이블 스키마

`resources/schema.sql`:

```
CREATE TABLE reservation (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

### 구현 전환

- 1단계에서 만든 조회 · 추가 · 삭제 API를 모두 JdbcTemplate 기반으로 전환한다
- 기존의 `List<Reservation>`, `AtomicLong`은 제거한다
- 예약 추가 시 DB가 생성한 `id`를 응답에 담는다

### 요구사항 테스트

아래 테스트가 모두 통과하면 단계 2 완료.

```
@Autowired
private JdbcTemplate jdbcTemplate;

@Test
void 데이터베이스_연동() {
    try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
        assertThat(connection).isNotNull();
        assertThat(connection.getCatalog()).isEqualTo("DATABASE");
        assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

@Test
void DB_조회_API_전환() {
    jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

    List<Reservation> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", Reservation.class);

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

    assertThat(reservations.size()).isEqualTo(count);
}

@Test
void DB_추가_삭제_API_전환() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2023-08-05");
    params.put("time", "10:00");

    RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200);

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    assertThat(count).isEqualTo(1);

    RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(200);

    Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    assertThat(countAfterDelete).isEqualTo(0);
}
```

## 🚀 3단계: 시간 관리

### 요구사항

관리자가 매번 예약 시간을 텍스트로 직접 입력해 번거롭고 실수가 나는 상황이다.
정해진 시간 슬롯을 관리자가 선택해서 쓸 수 있도록 시간 관리 기능을 추가하고 예약과 시간을 연결한다.

⚠️ 새로운 테스트 도구나 기법(Spring Boot Test, Mock, RestAssured 추가 활용 등)을 도입하지 않고 레벨1에서 학습했던 JUnit만 활용한 단위 테스트에 집중한다.
요구사항에서 RestAssured가 주어진 경우 그대로 사용하되, 그 위에 새 테스트 기법을 쌓지 않는다.

### 시간 관리 기능 추가

테이블 추가:

```
CREATE TABLE reservation_time (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

시간 API:

| 기능    | 메서드 / URL          | 요청 본문     | 응답                   |
|-------|--------------------|-----------|----------------------|
| 시간 추가 | POST /times        | {startAt} | {id, startAt}        |
| 시간 조회 | GET /times         | —         | [{id, startAt}, ...] |
| 시간 삭제 | DELETE /times/{id} | —         | 200 OK               |

### 예약과 시간 연결

- reservation 테이블의 time 컬럼을 time_id(FK → reservation_time.id)로 변경

```
CREATE TABLE reservation (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);
```

- Reservation 클래스의 time 필드를 String → ReservationTime 객체로 변경
- 예약 추가 요청 본문: time → timeId
- 예약 조회 응답: time을 객체로 ({id, startAt})

변경된 예약 추가 요청/응답 예시:

```
POST /reservations HTTP/1.1
Content-Type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": {
        "id": 1,
        "startAt": "10:00"
    }
}
```

### 요구사항 테스트

아래 테스트가 모두 통과하면 단계 3 완료.

```
@Test
void 시간_관리_API() {
    Map<String, String> params = new HashMap<>();
    params.put("startAt", "10:00");

    RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/times")
            .then().log().all()
            .statusCode(200);

    RestAssured.given().log().all()
            .when().get("/times")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));

    RestAssured.given().log().all()
            .when().delete("/times/1")
            .then().log().all()
            .statusCode(200);
}

@Test
void 예약과_시간_연결() {
    Map<String, Object> reservation = new HashMap<>();
    reservation.put("name", "브라운");
    reservation.put("date", "2023-08-05");
    reservation.put("timeId", 1);

    RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(reservation)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200);

    RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));
}
```
