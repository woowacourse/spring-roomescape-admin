package roomescape.integration;

import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.fixture.MemberFixture;
import roomescape.service.dto.LoginRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PageTest {

    private String adminToken;

    @BeforeEach
    void initAdminToken() {
        LoginRequest loginRequest = MemberFixture.loginRequest1();

        adminToken = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .extract()
                .cookie(LOGIN_TOKEN_HEADER_NAME);
    }

    @Test
    @DisplayName("관리자 페이지를 응답한다.")
    void adminPage() {
        RestAssured.given().log().all()
                .when().cookie(LOGIN_TOKEN_HEADER_NAME, adminToken).get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("관리자 예약 페이지를 응답한다.")
    void adminReservationPage() {
        RestAssured.given().log().all()
                .when().cookie(LOGIN_TOKEN_HEADER_NAME, adminToken).get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("관리자 시간 관리 페이지를 응답한다.")
    void adminTimePage() {
        RestAssured.given().log().all()
                .when().cookie(LOGIN_TOKEN_HEADER_NAME, adminToken).get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
