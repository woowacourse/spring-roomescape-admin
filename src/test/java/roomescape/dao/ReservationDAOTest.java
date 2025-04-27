package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

@JdbcTest
class ReservationDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("모든 reservation 을 조회한다")
    void findAllReservation() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);

        // when
        List<Reservation> reservations = reservationDAO.findAllReservation();

        // then
        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("reservation 을 추가한다")
    void insertReservation() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservation = new Reservation("fuyu", LocalDate.of(2025, 4, 28), LocalTime.of(12, 0));

        // when
        Long id = reservationDAO.insertReservation(reservation);

        // then
        assertThat(id != -1L).isTrue();

    @Test
    @DisplayName("reservation 을 삭제한다")
    void deleteReservationById() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservation = new Reservation("fuyu", LocalDate.of(2025, 4, 28), LocalTime.of(12, 0));
        Long id = reservationDAO.insertReservation(reservation);

        // when
        int deletedCount = reservationDAO.deleteReservationById(id);

        // then
        assertThat(deletedCount).isEqualTo(1);
    }
}
