package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 어드민_페이지를_요청하면_200_코드를_반환한다() {
        validatePageStatus("/admin", 200);
    }

    @Test
    void 예약_페이지를_요청하면_200_코드를_반환한다() {
        validatePageStatus("/admin/reservation", 200);
    }

    private void validatePageStatus(String path, int expectedStatusCode) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }
}
