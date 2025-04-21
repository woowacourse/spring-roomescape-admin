package roomescape.reservation.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getReservations() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);

        // when
        List<Reservation> reservations = reservationService.getReservations();

        // then
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void createReservation() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        ReservationRequest request = new ReservationRequest(
                LocalDate.of(2025, 4, 21),
                "미소",
                1L
        );

        // when
        Reservation created = reservationService.createReservation(request);

        // then
        Reservation expected = new Reservation(
                1L,
                "미소",
                LocalDate.of(2025, 4, 21),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                )
        );
        assertThat(created).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 예약을 삭제한다.")
    void deleteReservation() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);

        // when
        reservationService.deleteReservation(1L);

        // then
        List<Reservation> reservations = jdbcTemplate.query(
                "select r.id, r.name, r.date, rt.id as time_id, rt.start_at "
                        + "from reservation r "
                        + "inner join reservation_time rt on r.time_id = rt.id",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                LocalTime.parse(resultSet.getString("start_at"))
                        )
                ));
        assertThat(reservations.size()).isEqualTo(0);
    }
}
