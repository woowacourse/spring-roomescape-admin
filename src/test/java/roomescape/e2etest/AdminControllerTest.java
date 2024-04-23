package roomescape.e2etest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminControllerTest {

    @LocalServerPort
    int port;

    @DisplayName("로컬 서버가 200 statusCode 를 응답한다")
    @Test
    void testServerResponse() {
        RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("admin/reservation 가 200 statusCode 를 응답한다")
    @Test
    void testGetAdminReservation() {
        RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
