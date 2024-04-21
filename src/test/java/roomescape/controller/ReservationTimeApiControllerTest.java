package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeApiControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("모든 예약 시간을 조회한다.")
    @Test
    void findAllReservationTimes() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1L, "10:00");

        List<ReservationTime> reservationTimes = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);

        assertThat(reservationTimes.size()).isEqualTo(getReservationTimeCount());
    }

    @DisplayName("예약 시간을 추가한다.")
    @Test
    void addReservationTime() {
        Map<String, Object> params = new HashMap<>();
        params.put("startAt", "11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("startAt", is("11:00"));

        assertThat(getReservationTimeCount()).isEqualTo(1);
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Test
    void deleteReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1L, "10:00");

        assertThat(getReservationTimeCount()).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);

        assertThat(getReservationTimeCount()).isEqualTo(0);
    }

    private Integer getReservationTimeCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM reservation_time", Integer.class);
    }
}
