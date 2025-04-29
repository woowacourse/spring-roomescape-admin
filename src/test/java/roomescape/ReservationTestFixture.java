package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReservationTestFixture {
    public static Map<String, Object> createReservationRequestBody() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("timeId", 1);
        return params;
    }

    public static ValidatableResponse successGet(String path) {
        return RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200);
    }

    public static ValidatableResponseOptions<ValidatableResponse, Response> successPostWithJason(Object body, String path) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(path)
                .then().log().all()
                .statusCode(200);
    }

    public static ValidatableResponse successDelete(String path) {
        return RestAssured.given().log().all()
                .when().delete(path)
                .then().log().all()
                .statusCode(200);
    }
}
