package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationsResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationFixture {
    public static void 예약_생성(ReservationRequest reservationRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("name", reservationRequest.name());
        params.put("date", reservationRequest.date());
        params.put("time", reservationRequest.time());
        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when()
                   .post("/reservations")
                   .then()
                   .statusCode(201);
    }

    public static List<Reservation> 예약_조회() {
        return RestAssured.given()
                          .contentType(ContentType.JSON)
                          .when()
                          .get("/reservations")
                          .then()
                          .statusCode(200)
                          .extract()
                          .as(ReservationsResponse.class)
                          .reservations();
    }

    public static void 예약_삭제() {
        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .delete("/reservations/1")
                   .then()
                   .log()
                   .all()
                   .statusCode(204);
    }

}
