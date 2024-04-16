package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminControllerTest {

    @DisplayName("/admin 요청 시 어드민 메인 페이지가 응답한다.")
    @Test
    void adminTest() {
        //given
        String adminUrl = "/admin";

        //when & then
        RestAssured.given().log().all()
                .when().get(adminUrl)
                .then().log().all()
                .statusCode(200);
    }
}
