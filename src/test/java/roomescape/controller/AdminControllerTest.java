package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void moveToAdminPage() {
        RestAssured.given().log().all()
            .when().get("/admin")
            .then().log().all()
            .statusCode(200);
    }

    @DisplayName("예약 페이지 응답")
    @Test
    void moveToReservationPage() {
        RestAssured.given().log().all()
            .when().get("/admin/reservation")
            .then().log().all()
            .statusCode(200);
    }
}
