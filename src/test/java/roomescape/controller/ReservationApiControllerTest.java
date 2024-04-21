package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiControllerTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // 시간 데이터는 미리 추가한다.
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1L, "10:00");
    }

    @DisplayName("예약 정보를 조회한다.")
    @Test
    void findAllReservations() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)"
                , "상돌", "2024-04-21", 1L);

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".");

        Integer reservationCount = getReservationCount();
        assertThat(reservations.size()).isEqualTo(reservationCount);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addReservation() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "상돌");
        params.put("date", "2024-04-21");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("상돌"))
                .body("date", is("2024-04-21"))
                .body("time.id", is(1))
                .body("time.startAt", is("10:00"));
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteReservation() {
        jdbcTemplate.update("INSERT INTO reservation (id, name, date, time_id) VALUES (?, ?, ?, ?)"
                , 1L, "상돌", "2024-04-21", 1L);

        Integer reservationCountBeforeDelete = getReservationCount();
        assertThat(reservationCountBeforeDelete).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer reservationCountAfterDelete = getReservationCount();
        assertThat(reservationCountAfterDelete).isEqualTo(0);
    }

    private Integer getReservationCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);
    }
}
