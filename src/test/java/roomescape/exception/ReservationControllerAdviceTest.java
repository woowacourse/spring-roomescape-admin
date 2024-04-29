package roomescape.exception;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationControllerAdviceTest {

    @Test
    @DisplayName("잘못된 리소스를 찾을 경우 404 NOT FOUND 상태 코드를 반환한다. ")
    void invokeIllegalArgument_ShouldReturnNotFoundStateCode_WhenSearchDoesNotExistResources() {
        Map<String, String> params = Map.of("date", "2023-08-25", "name", "fram", "timeId", "-1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }
}
