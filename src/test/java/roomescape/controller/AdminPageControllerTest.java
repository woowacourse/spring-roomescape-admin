package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminPageControllerTest {

    @DisplayName("일단계: 홈화면 - /admin으로 요청이 들어오면 어드민 페이지를 응답한다.")
    @Test
    void step_one() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("이단계: 예약 조회 - /admin/reservation으로 요청이 들어오면 예약 페이지를 응답한다.")
    @Test
    void step_two() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/로 요청이 들어오면 웰컴 페이지를 응답한다.")
    @Test
    void welcome() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }
}
