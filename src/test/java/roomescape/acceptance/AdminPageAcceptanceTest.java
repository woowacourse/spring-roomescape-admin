package roomescape.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminPageAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("홈 화면을 요청하면 200 OK을 응답한다.")
    void adminPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 관리 페이지를 요청하면 200 OK를 반환한다.")
    void reservationPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 관리 페이지를 요청하면 200 OK를 반환한다.")
    void timePageTest() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
