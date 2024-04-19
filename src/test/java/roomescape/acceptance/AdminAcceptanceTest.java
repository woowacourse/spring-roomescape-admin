package roomescape.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminAcceptanceTest {
    private static final String PATH = "/admin";


    @Test
    void 일단계_관리자_메인_페이지를_응답한다() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 이단계_관리자의_예약_관리_페이지를_응답한다() {
        RestAssured.given().log().all()
                .when().get(PATH + "/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
