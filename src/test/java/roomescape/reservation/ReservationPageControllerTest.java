package roomescape.reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.TestSupport;

class ReservationPageControllerTest extends TestSupport {

    @Test
    @DisplayName("/reservation 를 요청하면 reservation.html 를 반환한다.")
    void requestAdminReservation() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);
    }

}
