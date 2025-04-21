package roomescape.reservationtime.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("시간을 조회하는 API를 요청한다.")
    void getReservationTimes() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        List<ReservationTime> reservationTimes = RestAssured
                .given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(reservationTimes.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("시간을 생성하는 API를 요청한다.")
    void createReservationTime() {
        // given
        var params = Map.of(
                "startAt", "10:00"
        );

        // when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    @DisplayName("시간을 삭제하는 API를 요청한다.")
    void deleteReservationTime() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        RestAssured
                .given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}
