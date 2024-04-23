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
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTimeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.update("delete from reservation_time");
    }

    @DisplayName("예약 시간 추가 테스트")
    @Test
    void createReservationTime() {
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationTimeRequest("10:00"))
                .when().post("/times")
                .then().log().all().extract().response();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("startAt")).isEqualTo("10:00")
        );
    }

    @DisplayName("모든 예약 시간 조회 테스트")
    @Test
    void findAllReservationTime() {
        Response response = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all().extract().response();

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".")).isEmpty()
        );
    }

    @DisplayName("예약 시간 삭제 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        //given
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00')");
        //then
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all().assertThat().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("예약 시간 삭제 실패 테스트")
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
