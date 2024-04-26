package roomescape.time;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.TestSupport;

class ReservationTimePageControllerTest extends TestSupport {

    @Test
    @DisplayName("/time 을 요청하면 time.html 를 반환한다.")
    void requestTime() {
        RestAssured.given().log().all()
                .when().get("/time")
                .then().log().all()
                .statusCode(200);
    }
}
