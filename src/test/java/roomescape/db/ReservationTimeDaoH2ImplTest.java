package roomescape.db;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationTimeDaoH2ImplTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약시간을 조회할 수 있다")
    void read() {
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);

        Assertions.assertThat(reservationTimeDaoH2Impl.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약시간을 저장할 수 있다")
    void save() {
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);

        reservationTimeDaoH2Impl.save(ReservationTime.from(LocalTime.now()));
        Assertions.assertThat(reservationTimeDaoH2Impl.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약시간을 삭제할 수 있다")
    void delete() {
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        ReservationTime saved = reservationTimeDaoH2Impl.save(ReservationTime.from(LocalTime.now()));

        reservationTimeDaoH2Impl.deleteById(saved.getId());

        Assertions.assertThat(reservationTimeDaoH2Impl.findAll()).isEmpty();
    }
}
