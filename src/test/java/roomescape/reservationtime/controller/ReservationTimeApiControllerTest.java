package roomescape.reservationtime.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationTimeApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("전체 시간 목록을 불러오는데 성공하면 200 코드를 반환한다.")
    void given_when_getTimes_then_statusCodeOk() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 등록에 성공하면 200 코드를 반환한다.")
    void given_validReservationTimeRequest_when_postReservation_then_statusCodeOk() {
        Map<String, Object> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:30");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 등록에 실패하면 400 코드를 반환한다.")
    void given_invalidReservationTimeRequest_when_postReservation_then_statusCodeBadRequest() {
        Map<String, Object> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("등록된 시간 삭제에 성공하면 204 코드를 반환한다.")
    void given_when_deleteReservation_then_statusCodeNoContents() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }
}