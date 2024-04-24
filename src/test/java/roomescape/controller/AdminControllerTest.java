package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminControllerTest {

    @Test
    @DisplayName("어드민 메인 페이지를 응답한다.")
    void mainPage() {
        RestAssured.given().log().all()
            .when().get("/admin")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    @DisplayName("예약 관리 페이지를 응답한다.")
    void reservationPage() {
        RestAssured.given().log().all()
            .when().get("/admin/reservation")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    @DisplayName("시간 관리 페이지를 응답한다.")
    void timePage() {
        RestAssured.given().log().all()
            .when().get("/admin/time")
            .then().log().all()
            .statusCode(200);
    }
}
