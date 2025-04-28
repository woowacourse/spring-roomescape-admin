package roomescape.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.common.SpringBootTestBase;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationTimeDbFixture;

class ReservationTimeApiTest extends SpringBootTestBase {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationTimeDbFixture reservationTimeDbFixture;

    @BeforeEach
    void setUp() {
        reservationTimeDbFixture = new ReservationTimeDbFixture(jdbcTemplate);
    }
    @Test
    void 예약_시간을_생성한다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "startAt", "10:00"
                ))
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        SoftAssertions softly = new SoftAssertions();

        List<ReservationTime> allReservationTime = findAllReservationTime();
        softly.assertThat(allReservationTime).hasSize(1);

        ReservationTime reservationTime = allReservationTime.getFirst();
        softly.assertThat(reservationTime.id()).isEqualTo(1L);
        softly.assertThat(reservationTime.time()).isEqualTo(LocalTime.of(10, 0, 0));
    }

    @Test
    void 예약_시간을_조회한다() {
        ReservationTime reservationTime = reservationTimeDbFixture.예약_시간_생성_10시();

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        SoftAssertions softly = new SoftAssertions();

        List<ReservationTime> allReservationTime = findAllReservationTime();
        softly.assertThat(allReservationTime).hasSize(1);

        ReservationTime savedReservation = allReservationTime.getFirst();
        softly.assertThat(savedReservation.id()).isEqualTo(reservationTime.id());
        softly.assertThat(savedReservation.time()).isEqualTo(reservationTime.time());
    }

    @Test
    void 예약_시간을_삭제한다() {
        reservationTimeDbFixture.예약_시간_생성_10시();

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
        assertThat(findAllReservationTime()).hasSize(0);
    }


    private List<ReservationTime> findAllReservationTime() {
        return jdbcTemplate.query("select * from reservation_time", (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        ));
    }
}
