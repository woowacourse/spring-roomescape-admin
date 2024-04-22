package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        createInitialReservationTime();
        final Map<String, Object> params = createReservationParam();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        final Integer actual = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservations() {
        createInitialReservationTime();
        createInitialReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        createInitialReservationTime();
        createInitialReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        final Integer actual = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(actual).isEqualTo(0);
    }

    private void createInitialReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:40");
    }

    private void createInitialReservation() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "냥인", "2024-04-21", 1L);
    }

    private Map<String, Object> createReservationParam() {
        return Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );
    }
}
