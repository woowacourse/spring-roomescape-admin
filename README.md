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
