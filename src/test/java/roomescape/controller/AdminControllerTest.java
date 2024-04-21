package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminControllerTest {

    @DisplayName("/admin 에 접속한다.")
    @Test
    void admin() {
        runTest("/admin");
    }

    @DisplayName("/admin/reservation 에 접속한다.")
    @Test
    void reservation() {
        runTest("/admin/reservation");
    }

    @DisplayName("/admin/time 에 접속한다.")
    @Test
    void time() {
        runTest("/admin/time");
    }

    private void runTest(String url) {
        RestAssured.given().log().all()
                .when().get(url)
                .then().log().all()
                .statusCode(200);
    }
}
