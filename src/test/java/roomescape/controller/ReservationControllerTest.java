package roomescape.controller;

import static org.junit.jupiter.api.Assertions.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약 생성을 요청하면 201 CREATE를 응답한다.")
    void postReservationTest() {
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(sql, "11:00");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
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
