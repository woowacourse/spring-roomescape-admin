package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationControllerTest {

    @DisplayName("존재하지 않는 예약 삭제")
    @Test
    void deleteNonExistentReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }
}
