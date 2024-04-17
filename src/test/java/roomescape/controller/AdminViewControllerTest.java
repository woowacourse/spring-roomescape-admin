package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

class AdminViewControllerTest extends BaseControllerTest {

    @Test
    void adminPage() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void adminReservationPage() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
