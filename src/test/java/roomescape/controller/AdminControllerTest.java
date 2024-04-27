package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void initializePort() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("홈 화면을 요청하면 200 OK을 응답한다.")
    void adminPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자 예약 화면을 요청하면 200 OK를 응답한다.")
    void adminReservationPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
