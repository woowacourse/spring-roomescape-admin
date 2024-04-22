package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class ReservationTimeFixture {
    public static void 예약_시간_생성(String startAt) {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", startAt);
        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when()
                   .post("/times")
                   .then()
                   .statusCode(201);
    }
}
