package roomescape;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    @DisplayName("관리자 홈 화면 핵심 태그 존재 여부를 확인한다.")
    @Test
    void containsCoreTagsInAdmin() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200)
                .body(containsString("<title>방탈출 어드민</title>"))
                .body(containsString("<a class=\"nav-link\" href=\"/admin/reservation\">Reservation</a>"))
                .body(containsString("<a class=\"nav-link\" href=\"/time\">Time</a>"));
    }
}
