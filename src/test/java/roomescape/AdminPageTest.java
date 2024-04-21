package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminPageTest {
    @Test
    void path_admin_offer_page() {
        RestAssured.given()
                   .when()
                   .get("/admin")
                   .then()
                   .statusCode(200);
    }

    @Test
    void path_admin_reservation_offer_page() {
        RestAssured.given()
                   .when()
                   .get("/admin/reservation")
                   .then()
                   .statusCode(200);
    }

}
