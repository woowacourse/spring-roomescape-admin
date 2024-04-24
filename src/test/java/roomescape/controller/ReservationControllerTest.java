package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @LocalServerPort
    int port;

    @BeforeEach
    void initializePort() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("예약 생성을 요청하면 201 CREATE를 응답한다.")
    void postReservationTest() {
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(sql, "11:00");

        ReservationRequest reservationRequest = new ReservationRequest(
                "브라운", LocalDate.of(2023, 8, 5), 1L
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("예약 읽기를 요청하면 200 OK를 응답한다.")
    void getReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 삭제를 요청하면 204 NO CONTENT를 응답한다.")
    void deleteReservationTest() {
        String timeSql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(timeSql, "11:00");

        String reservationSql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.update(reservationSql, "시소", "2024-04-23", 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

}
