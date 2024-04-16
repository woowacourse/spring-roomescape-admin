package roomescape;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminControllerTest {

    @DisplayName("Admin Page 홈화면 접근 성공 테스트")
    @Test
    void responseAdminPage() {
        Response response = RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all().extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("Admin Reservation Page 접근 성공 테스트")
    @Test
    void responseAdminReservationPage() {
        Response response = RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all().extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
