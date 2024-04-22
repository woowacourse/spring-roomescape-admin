package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from reservation");
    }

    @DisplayName("예약 추가 테스트")
    @Test
    void createReservation() {
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", "2023-08-05", "15:40"))
                .when().post("/reservations")
                .then().log().all().extract().response();

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo("브라운"),
                () -> assertThat(response.jsonPath().getString("date")).isEqualTo("2023-08-05"),
                () -> assertThat(response.jsonPath().getString("time")).isEqualTo("15:40")
        );
    }

    @DisplayName("모든 예약 내역 조회 테스트")
    @Test
    void findAllReservations() {
        //when
        Response response = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response();
        //then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".")).isEmpty()
        );
    }

    @DisplayName("예약 취소 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        //given
        createReservation();
        long id = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract()
                .jsonPath().getList("id", Long.class).get(0);
        //then
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all().assertThat().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("예약 취소 실패 테스트")
    @Test
    void deleteReservationFail() {
        //given
        long invalidId = 0;
        //then
        RestAssured.given().log().all()
                .when().delete("/reservations/" + invalidId)
                .then().log().all().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }
}
