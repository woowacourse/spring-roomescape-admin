package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomescapeAdminControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void environmentSetUp() {
        RestAssured.port = port;
    }

    @DisplayName("어드민 메인 페이지 응답 성공 테스트")
    @Test
    void admin() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 관리 페이지 응답 성공 테스트")
    @Test
    void adminReservation() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
