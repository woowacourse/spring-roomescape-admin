package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.time.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, __) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()
            )
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static Map<String, Object> reservationParams() {
        return Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1
        );
    }

    static Map<String, Object> timeParams() {
        return Map.of("startAt", "10:00");
    }

    void insertReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES '10:00'");
    }

    void insertReservation() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) "
                        + "VALUES (?, ?, SELECT t.id FROM reservation_time t WHERE t.id = ?)",
                "브라운", "2023-08-05", 1
        );
    }

    @BeforeEach
    void setUp() {
//        insertReservationTime();
        System.out.println(jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper));
    }

    @DisplayName("예약 내역을 조회한다.")
    @Test
    void findAll() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void create() {
        insertReservationTime();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        insertReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
