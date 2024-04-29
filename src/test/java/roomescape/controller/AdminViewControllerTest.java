package roomescape.controller;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminViewControllerTest {
    @DisplayName("어드민 메인 페이지 요청 테스트")
    @Test
    void adminMainPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("어드민 예약 페이지 요청 테스트")
    @Test
    void adminReservationPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("어드민 타임 페이지 요청 테스트")
    @Test
    void adminTimePageTest() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("메인 페이지를 요청하면 어드민 메인 페이지로 리다이렉트한다.")
    @Test
    void mainPageRedirectionTest() {
        RestAssured.given()
                .redirects().follow(false)
                .when().get("http://localhost:8080")
                .then()
                .statusCode(302)
                .header("Location", equalTo("http://localhost:8080/admin"));
    }

    @DisplayName("예약 페이지를 요청하면 어드민 예약 페이지로 리다이렉트한다.")
    @Test
    void reservationPageRedirectionTest() {
        RestAssured.given()
                .redirects().follow(false)
                .when().get("http://localhost:8080/reservation")
                .then()
                .statusCode(302)
                .header("Location", equalTo("http://localhost:8080/admin/reservation"));
    }

    @DisplayName("게임 시간 페이지를 요청하면 어드민 시간 페이지로 리다이렉트한다.")
    @Test
    void timePageRedirectionTest() {
        RestAssured.given()
                .redirects().follow(false)
                .when().get("http://localhost:8080/time")
                .then()
                .statusCode(302)
                .header("Location", equalTo("http://localhost:8080/admin/time"));
    }
}
