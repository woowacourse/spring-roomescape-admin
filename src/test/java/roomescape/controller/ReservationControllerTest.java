package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    @Test
    void reservation_url로_요청하면_예약_페이지를_반환한다() {
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .when().get("http://localhost:8080/admin/reservation")
                .then().log().all()
                .statusCode(200).extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.asString()).contains("방탈출 예약 페이지");
    }
}
