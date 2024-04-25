package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {
    @Test
    @DisplayName("admin에 get 요청을 하면 ok를 반환한다")
    void admin() {
        RestAssured.when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("admin/reservation에 get 요청을 하면 ok를 반환한다")
    void reservation() {
        RestAssured.when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("admin/time에 get 요청을 하면 ok를 반환한다")
    void time() {
        RestAssured.when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
