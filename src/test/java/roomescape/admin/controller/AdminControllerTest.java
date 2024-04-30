package roomescape.admin.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:db/truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @DisplayName("어드민 예약 정보 관리 페이지 테스트")
    @Test
    void adminReservationPageReadTest() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("어드민 예약 시간 관리 페이지 테스트")
    @Test
    void adminReservationTimePageReadTest() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
