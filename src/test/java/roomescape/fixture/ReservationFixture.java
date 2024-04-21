package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.dto.ReservationRequest;

import java.util.HashMap;
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
}
