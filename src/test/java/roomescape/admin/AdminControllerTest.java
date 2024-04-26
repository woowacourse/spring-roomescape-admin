package roomescape.admin;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.TestSupport;

public class AdminControllerTest extends TestSupport {

    @Test
    @DisplayName("root url 을 요청하면 welcome page 를 반환한다.")
    void requestRootUrl() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("/admin 을 요청하면 index.html 를 반환한다.")
    void requestAdmin() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }
}
