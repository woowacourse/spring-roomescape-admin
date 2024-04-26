package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.dto.response.ReservationTimeCreateResponse;

import java.util.HashMap;
import java.util.Map;

public class ReservationTimeFixture {
    public static long 예약_시간_생성(String startAt) {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", startAt);
        return RestAssured.given()
                          .contentType(ContentType.JSON)
                          .body(params)
                          .when()
                          .post("/times")
                          .then()
                          .statusCode(201)
                          .extract()
                          .as(ReservationTimeCreateResponse.class)
                          .id();
    }
}
