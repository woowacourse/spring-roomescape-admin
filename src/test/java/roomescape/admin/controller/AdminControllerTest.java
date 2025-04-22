package roomescape.admin.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @Test
    @DisplayName("어드민 페이지를 반환한다.")
    void getAdminPage() {
        RestAssured
                .given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 페이지를 반환한다.")
    void getReservationPage() {
        RestAssured
                .given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 페이지를 반환한다.")
    void getTimePage() {
        RestAssured
                .given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
