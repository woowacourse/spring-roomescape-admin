package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class ReservationFixture {

    public static Long generateReservationTime(String startAt) {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", startAt);

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    public static Map<String, Object> generateReservationParams(String name, String date, Long timeId) {
        Map<String, Object> reservationParams = new HashMap<>();
        reservationParams.put("name", name);
        reservationParams.put("date", date);
        reservationParams.put("timeId", timeId);
        return reservationParams;
    }

}
