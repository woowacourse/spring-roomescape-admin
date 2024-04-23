package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.lang.reflect.Field;
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

    @Autowired
    private ReservationController reservationController;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.update("delete from reservation_time");
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

    @DisplayName("예약 추가 테스트")
    @Test
    void createReservation() {
        //given
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00')");
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", "2023-08-05", 1))
                .when().post("/reservations")
                .then().log().all().extract().response();
        //then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo("브라운"),
                () -> assertThat(response.jsonPath().getString("date")).isEqualTo("2023-08-05"),
                () -> assertThat(response.jsonPath().getLong("time.id")).isEqualTo(1),
                () -> assertThat(response.jsonPath().getString("time.startAt")).isEqualTo("10:00")
        );
    }

    @DisplayName("예약 취소 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        //given
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00:00')");
        jdbcTemplate.update("INSERT INTO reservation VALUES (1, 'brown', '2024-11-15', 1)");
        //then
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
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

    @DisplayName("JDBC 주입 여부 테스트")
    @Test
    void jdbcTemplateNotInjected() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
