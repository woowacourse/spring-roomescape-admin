package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/test-schema.sql")
class AdminControllerTest {

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void moveToAdminPage() {
        RestAssured.given().log().all()
            .when().get("/admin")
            .then().log().all()
            .statusCode(200);
    }

    @DisplayName("예약 관리 페이지 응답")
    @Test
    void moveToReservationPage() {
        RestAssured.given().log().all()
            .when().get("/admin/reservation")
            .then().log().all()
            .statusCode(200);
    }

    @DisplayName("시간 관리 페이지 응답")
    @Test
    void moveToTimePage() {
        RestAssured.given().log().all()
            .when().get("/admin/time")
            .then().log().all()
            .statusCode(200);
    }
}
