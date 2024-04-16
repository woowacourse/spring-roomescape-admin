package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @DisplayName("/admin 요청 시 어드민 메인 페이지가 응답한다.")
    @Test
    void admin() {
        //given
        String adminUrl = "/admin";

        //when & then
        RestAssured.given().log().all()
                .when().get(adminUrl)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin/reservation 요청 시 예약 관리 페이지가 응답한다.")
    @Test
    void reservation() {
        //given
        String reservationUrl = "/admin/reservation";

        //when & then
        RestAssured.given().log().all()
                .when().get(reservationUrl)
                .then().log().all()
                .statusCode(200);
    }
}
