package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Test
    @DisplayName("예약 시간을 성공적으로 추가하면 ok를 반환한다.")
    void createReservationTime() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("10:00"));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 시간 목록을 성공적으로 조회하면 ok를 반환하고, 바디의 사이즈를 확인한다.")
    void getReservationTimes() {
        createInitialReservationTime();

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 시간을 성공적으로 삭제하면 noContent를 반환한다.")
    void deleteReservationTime() {
        final int id = createInitialReservationTime();

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(204);
    }

    private int createInitialReservationTime() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("10:00"));

        final ExtractableResponse<Response> reservationTime = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract();

        return reservationTime.jsonPath().get("id");
    }
}
