package roomescape.exception;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeControllerAdviceTest {


    @Test
    @DisplayName("의도하지 않은 상태에서 DB 접근 시 400 BAD REQUEST 상태 코드를 반환한다.")
    void invokeIllegalStatement_ShouldReturnBadRequestStateCode_WhenAccessUnintendedCondition() {
        final var params = Map.of("date", "2023-08-25", "name", "fram", "timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(400);
    }

}
