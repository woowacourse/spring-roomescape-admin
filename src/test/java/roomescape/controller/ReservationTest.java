package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ReservationTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        namedParameterJdbcTemplate.update("DELETE FROM reservation", new MapSqlParameterSource());
        namedParameterJdbcTemplate.update("DELETE FROM reservation_time", new MapSqlParameterSource());
    }

    private Long createTime() {
        Map<String, String> time = new HashMap<>();
        time.put("startAt", "10:00");

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post("/times")
                .then().extract().body().jsonPath().getLong("id");
    }

    @Test
    @DisplayName("예약 목록을 조회할 수 있다.")
    void get() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0)); // 아직 생성 요청이 없으니 0개

        createReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 추가할 수 있다.")
    void create() {
        Long timeId = createTime();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 추가시, 예약시간이 존재하지 않으면 BadRequest을 반환한다. ")
    void reservationTimeNotExists_returnBadRequest() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "티온");
        reservation.put("date", "2026-05-03");
        reservation.put("timeId", 2);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약 추가시, 날짜 형식이 맞지 않는다면 예외를 반환한다.")
    void reservationTimeNotExists() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "티온");
        reservation.put("date", "2026:05:03");
        reservation.put("timeId", 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다.")
    void delete() {
        Long id = createReservation();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private Long createReservation() {
        Long timeId = createTime();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "티온");
        reservation.put("date", "2026-05-03");
        reservation.put("timeId", timeId);

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().extract()
                .body().jsonPath().getLong("id");
    }

}
