package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
}
