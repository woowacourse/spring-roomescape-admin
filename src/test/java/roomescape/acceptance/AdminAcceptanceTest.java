package roomescape.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminAcceptanceTest {
    private static final String PATH = "/admin";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("[1단계 - 홈 화면] 관리자 메인 페이지를 응답한다")
    @Test
    void step1() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("[2단계 - 예약 조회] 관리자의 예약 관리 페이지를 응답한다")
    @Test
    void step2() {
        RestAssured.given().log().all()
                .when().get(PATH + "/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
