package roomescape.controller;

import static org.hamcrest.CoreMatchers.is;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @DisplayName("예약 시간을 추가하면 201 응답이 온다.")
    @Test
    void addReservationTime() {
        Map<String, String> params = Map.of("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1))
                .body("startAt", is("10:00"));
    }

    @DisplayName("모든 예약 시간을 조회하면 200 응답이 온다.")
    @Test
    void findAllReservationTimes() {
        Map<String, String> params = Map.of("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약 시간을 삭제하면 200 응답이 온다.")
    @Test
    void deleteReservation() {
        Map<String, String> params = Map.of("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
