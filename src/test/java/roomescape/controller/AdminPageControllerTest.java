package roomescape.controller;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminPageControllerTest {

    @DisplayName("관리자 홈 화면 핵심 태그 존재 여부를 확인한다.")
    @Test
    void containsCoreTagsInAdminTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200)
                .body(containsString("<title>방탈출 어드민</title>"))
                .body(containsString("<a class=\"nav-link\" href=\"/admin/reservation\">Reservation</a>"))
                .body(containsString("<a class=\"nav-link\" href=\"/admin/time\">Time</a>"));
    }

    @DisplayName("예약 시간 관리 화면 핵심 태그 존재 여부를 확인한다.")
    @Test
    void containsCoreTagsInAdminTimeTest() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200)
                .body(containsString("<h2 class=\"content-container-title\">시간 관리 페이지</h2>"))
                .body(containsString("<button class=\"btn btn-custom mb-2 float-right\" id=\"add-button\">예약 추가</button>"))
                .body(containsString("<th scope=\"col\">순서</th>"))
                .body(containsString("<th scope=\"col\">시간</th>"));
    }
}
