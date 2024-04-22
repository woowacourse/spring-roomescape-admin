package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

@SpringBootTest
class ReservationTimeDAOTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeDAO reservationTimeDAO;

    @DisplayName("모든 예약 시간을 조회한다")
    @Test
    void should_get_reservations() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) values (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) values (?)", "11:00");
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservations();
        assertThat(reservationTimes).hasSize(2);
    }

}
