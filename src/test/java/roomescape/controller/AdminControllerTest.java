package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @DisplayName("홈 화면 조회")
    @Test
    void responseHome() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().statusCode(200);
    }

    @DisplayName("어드민 화면 조회")
    @Test
    void responseAdmin() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().statusCode(200);
    }
}
