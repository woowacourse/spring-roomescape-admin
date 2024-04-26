package roomescape.core.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.web.repository.JdbcReservationRepository;
import roomescape.web.repository.JdbcReservationTimeRepository;

@JdbcTest
class JdbcReservationRepositoryTest {

    private final JdbcReservationRepository jdbcReservationRepository;
    private final JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @Autowired
    public JdbcReservationRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcReservationRepository = new JdbcReservationRepository(jdbcTemplate);
        this.jdbcReservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @DisplayName("예약 시간이 존재하지 않는지 확인한다.")
    @Test
    void existFalse() {
        Boolean result = jdbcReservationRepository.existByTimeId(1L);

        assertThat(result).isFalse();
    }

    @DisplayName("예약 시간이 존재하는지 확인한다.")
    @Test
    void existTrue() {
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:10")));
        jdbcReservationRepository.save(new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));

        Boolean result = jdbcReservationRepository.existByTimeId(reservationTime.getId());

        // then
        assertThat(result).isTrue();
    }
}

