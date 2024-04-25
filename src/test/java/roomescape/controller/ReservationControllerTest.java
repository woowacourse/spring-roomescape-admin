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
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateRequest;

import java.time.LocalTime;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    @DisplayName("예약을 성공적으로 추가하면 ok를 반환한다.")
    void createReservation() {
        createInitialReservationTime();
        final ReservationCreateRequest request = new ReservationCreateRequest("냥인", "2024-04-24", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 목록을 성공적으로 조회하면 ok를 반환하고, 바디의 사이즈를 확인한다.")
    void readReservations() {
        createInitialReservationTime();
        createInitialReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 성공적으로 삭제하면 noContent를 반환한다.")
    void deleteReservation() {
        createInitialReservationTime();
        final int id = createInitialReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(204);
    }

    private void createInitialReservationTime() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("10:00"));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private int createInitialReservation() {
        final ReservationCreateRequest request = new ReservationCreateRequest("냥인", "2024-04-24", 1L);

        final ExtractableResponse<Response> reservation = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract();
        return reservation.jsonPath().get("id");
    }
}
