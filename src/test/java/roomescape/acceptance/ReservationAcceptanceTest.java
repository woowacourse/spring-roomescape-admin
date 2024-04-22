package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.is;

class ReservationAcceptanceTest extends BasicAcceptanceTest {
    @DisplayName("저장된 예약 리스트를 조회한다")
    @Test
    void reservationsTest() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약을 추가하고 삭제한다")
    @Test
    void reservationAddRemoveTest() {
        Map<String, String> paramsA = Map.of("startAt", "10:00");

        int reservationTimeId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(paramsA)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("id");

        Map<String, String> paramsB = Map.ofEntries(
                Map.entry("name", "브라운"),
                Map.entry("date", String.valueOf(LocalDate.now().plusDays(1))),
                Map.entry("timeId", String.valueOf(reservationTimeId)));

        int reservationId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(paramsB)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .extract()
                .path("id");

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/" + reservationId)
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));

        RestAssured.given().log().all()
                .when().delete("/times/" + reservationTimeId)
                .then().log().all()
                .statusCode(204);
    }
}
