package roomescape.reservation.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @LocalServerPort
    int randomServerPort;

    @DisplayName("예약 시간 조회 테스트")
    @Test
    void getReservationTime() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("reservationTimes.size()", is(0));
    }

    @DisplayName("예약 시간 등록 테스트")
    @Test
    void addReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("reservationTimes.size()", is(1));
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Test
    void deleteReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }
}
