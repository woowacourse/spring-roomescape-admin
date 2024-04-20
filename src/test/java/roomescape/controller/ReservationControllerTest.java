package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.ReservationDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationDto reservationDto;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        String name = "브라운";
        String date = "2023-08-05";
        String time = "15:40";
        reservationDto = ReservationDto.of(null, name, date, time);
    }

    @Test
    @DisplayName("전체 예약을 조회한다.")
    void getAllReservationsTest() {
        int count = getCount();
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count));
    }

    @Test
    @DisplayName("예약을 성공적으로 추가한다.")
    void addReservationTest() {
        int count = getCount();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count + 1));
    }

    @Test
    @DisplayName("예약을 성공적으로 삭제한다.")
    void deleteReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        int count = getCount();

        RestAssured.given()
                .pathParam("id", 1L)
                .log().all()
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count - 1));
    }

    private int getCount() {
        String sql = "SELECT COUNT(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
