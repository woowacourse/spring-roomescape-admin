package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @LocalServerPort
    int randomServerPort;

    @DisplayName("어드민 페이지 조회 테스트")
    @Test
    void adminPageReadTest() {
        RestAssured.
                given().log().all()
                .port(randomServerPort)
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("어드민 예약 정보 조회 페이지 테스트")
    @Test
    void adminReservationPageReadTest() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
