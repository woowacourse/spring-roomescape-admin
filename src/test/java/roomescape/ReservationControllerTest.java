package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.port = port;

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response().jsonPath().getList("id")
                .forEach(id -> RestAssured.given().log().all()
                        .when().delete("/reservations/" + id));
    }

    @DisplayName("예약 추가 테스트")
    @Test
    void createReservation() {
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", "2023-08-05", "15:40"))
                .when().post("/reservations")
                .then().log().all().extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("모든 예약 내역 조회 테스트")
    @Test
    void findAllReservations() {
        createReservation();

        Response response = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response();

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("", ReservationResponse.class)).hasSize(1)
        );
    }

    @DisplayName("예약 취소 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        createReservation();

        var id = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response().jsonPath().getList("id").get(0);
        Response deleteResponse = RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all().extract().response();
        Response getResponse = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response();

        assertAll(
                () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(getResponse.jsonPath().getList("", ReservationResponse.class)).hasSize(0)
        );
    }

    @DisplayName("예약 취소 실패 테스트")
    @Test
    void deleteReservationFail() {
        Response response = RestAssured.given().log().all()
                .when().delete("/reservations/0")
                .then().log().all().extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
