package roomescape.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;
import static roomescape.fixture.ReservationNameFixture.한스;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.common.SpringBootTestBase;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationDateFixture;
import roomescape.fixture.ReservationDbFixture;
import roomescape.fixture.ReservationTimeDbFixture;

class ReservationApiTest extends SpringBootTestBase {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeDbFixture reservationTimeDbFixture;
    private ReservationDbFixture reservationDbFixture;

    @BeforeEach
    void setUp() {
        reservationTimeDbFixture = new ReservationTimeDbFixture(jdbcTemplate);
        reservationDbFixture = new ReservationDbFixture(jdbcTemplate);
    }

    @Test
    void 예약_정보를_조회한다() {
        ReservationTime reservationTime = reservationTimeDbFixture.예약_시간_생성_10시();
        reservationDbFixture.예약_생성_한스_25_4_22(reservationTime);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_정보를_생성한다() {
        ReservationTime reservationTime = reservationTimeDbFixture.예약_시간_생성_10시();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", 한스.getValue(),
                        "date", ReservationDateFixture.예약일_25_4_22.date().toString(),
                        "timeId", reservationTime.id()
                ))
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        SoftAssertions softly = new SoftAssertions();

        List<Reservation> allReservation = getAllReservation();
        softly.assertThat(allReservation).hasSize(1);

        Reservation reservation = allReservation.getFirst();
        softly.assertThat(reservation.id()).isEqualTo(1);
        softly.assertThat(reservation.name()).isEqualTo(한스.getValue());
        softly.assertThat(reservation.date()).isEqualTo(ReservationDateFixture.예약일_25_4_22.date());
        softly.assertThat(reservation.id()).isEqualTo(reservationTime.id());
    }

    @Test
    void 예약_정보를_삭제한다() {
        ReservationTime reservationTime = reservationTimeDbFixture.예약_시간_생성_10시();

        reservationDbFixture.예약_생성_한스_25_4_22(reservationTime);
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        assertThat(getAllReservation()).hasSize(0);
    }

    private List<Reservation> getAllReservation() {
        String sql = """
                SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value
                FROM reservation as r inner join reservation_time as t on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                new ReservationName(resultSet.getString("name")),
                new ReservationDate(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        ));
    }
}
