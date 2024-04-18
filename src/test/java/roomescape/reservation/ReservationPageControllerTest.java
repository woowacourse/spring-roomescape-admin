package roomescape.reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationPageControllerTest {
    
    @Test
    @DisplayName("/admin/reservation 를 요청하면 reservation-legacy.html 를 반환한다.")
    void requestAdminReservation() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

}
