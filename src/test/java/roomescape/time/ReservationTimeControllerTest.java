package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("시간을 조회한다.")
    @Test
    void findAll() {
        insertReservationTime(jdbcTemplate, "09:00");
        insertReservationTime(jdbcTemplate, "10:00");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @DisplayName("시간을 추가한다.")
    @Test
    void create() {
        insertReservationTime(jdbcTemplate, "11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime())
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RESERVATION_TIME", Integer.class);
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("시간을 삭제한다.")
    @Test
    void delete() {
        insertReservationTime(jdbcTemplate, "12:00");
        insertReservationTime(jdbcTemplate, "13:00");

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) from RESERVATION_TIME", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    static ReservationTime reservationTime() {
        return new ReservationTime(0, LocalTime.parse("10:00"));
    }

    static void insertReservationTime(JdbcTemplate jdbcTemplate, String time) {
        jdbcTemplate.update("INSERT INTO RESERVATION_TIME (START_AT) VALUES (?)", time);
    }
}
