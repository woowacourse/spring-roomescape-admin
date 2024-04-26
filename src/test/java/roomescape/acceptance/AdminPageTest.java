package roomescape.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminPageTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

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

    @Test
    void path_admin_time_offer_page() {
        RestAssured.given()
                   .when()
                   .get("/admin/time")
                   .then()
                   .statusCode(200);
    }

}
