package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @Test
    @DisplayName("어드민 메인 페이지를 응답한다.")
    void mainPage() {
        assertGetRequestStatusCodeOK("/admin");
    }

    @Test
    @DisplayName("예약 관리 페이지를 응답한다.")
    void reservationPage() {
        assertGetRequestStatusCodeOK("/admin/reservation");
    }

    private void assertGetRequestStatusCodeOK(final String path) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200);
    }
}
