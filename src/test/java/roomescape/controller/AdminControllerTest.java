package roomescape.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
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

    @DisplayName("예약 시간 화면 조회")
    @Test
    void responseTime() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().statusCode(200);
    }
}
