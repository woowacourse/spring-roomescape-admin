package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static roomescape.fixture.DateTimeFixture.DAY_AFTER_TOMORROW;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {
    private int time_id;

    @BeforeEach
    void setUp() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        time_id = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract().path("id");
    }

    @AfterEach
    void afterEach() {
        RestAssured.given().log().all()
                .when().delete("/times/" + time_id)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("전체 예약 정보 요청 테스트")
    @Test
    void reservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약 생성, 삭제 테스트")
    @Test
    void reservationCreationAndDeleteTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", DAY_AFTER_TOMORROW.toString());
        params.put("timeId", String.valueOf(time_id));

        int savedId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract().path("id");

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/" + savedId)
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

}
