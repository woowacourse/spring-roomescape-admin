package roomescape.db;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationDaoH2ImplTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    ReservationTimeDaoH2Impl reservationTimeDaoH2Impl;
    ReservationTime saved;

    @BeforeEach
    void setUp() {
        reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        final ReservationTime reservationTime = ReservationTime.from(LocalTime.now());
        saved = reservationTimeDaoH2Impl.save(reservationTime);
    }


    @Test
    @DisplayName("예약을 조회할 수 있다")
    void read() {
        final ReservationDaoH2Impl reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);

        Assertions.assertThat(reservationDaoH2Impl.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약을 저장할 수 있다")
    void save() {
        final ReservationDaoH2Impl reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);

        reservationDaoH2Impl.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDaoH2Impl.findById(saved.getId()).get()));
        Assertions.assertThat(reservationDaoH2Impl.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다")
    void delete() {
        final ReservationDaoH2Impl reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);
        reservationDaoH2Impl.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDaoH2Impl.findById(saved.getId()).get()));

        reservationDaoH2Impl.deleteById(saved.getId());

        Assertions.assertThat(reservationDaoH2Impl.findAll()).isEmpty();
    }
}
