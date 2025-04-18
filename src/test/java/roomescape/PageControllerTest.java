package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PageControllerTest {
    @DisplayName("/ 페이지 연결 테스트(웰컴 페이지)")
    @Test
    void welcomePage() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin 페이지 연결 테스트")
    @Test
    void adminPage() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin/reservation 페이지 연결 테스트")
    @Test
    void adminReservationPage() {
        RestAssured.given().log().all()
                .when().get("admin/reservation")
                .then().log().all()
                .statusCode(200);
    }
}
