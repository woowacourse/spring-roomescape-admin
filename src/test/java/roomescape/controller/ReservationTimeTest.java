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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ReservationTimeTest {
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

    @Test
    @DisplayName("예약 시간을 추가할 수 있다.")
    void create() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약시간을 조회할 수 있다.")
    void get() {
        createTime();
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약시간을 삭제할 수 있다.")
    void delete() {
        Long id = createTime();
        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("시간 삭제시, 예약이 존재할 경우 BadRequest를 반환한다.")
    void deleteReservationTime_existsReservation_IllegalArgument() {
        Long id = createTime();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", id);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private Long createTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .post("/times")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getLong("id");
    }
}
