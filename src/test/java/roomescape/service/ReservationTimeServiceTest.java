package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.db.ReservationDao;
import roomescape.db.ReservationDaoH2Impl;
import roomescape.db.ReservationTimeDao;
import roomescape.db.ReservationTimeDaoH2Impl;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("이미 예약된 시간 삭제하려고 하면 예외가 발생한다")
    void create() {
        final ReservationDao reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);
        final ReservationTimeDao reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDaoH2Impl,
                reservationDaoH2Impl);
        final ReservationTime reservationTime = ReservationTime.from(LocalTime.now());

        reservationTimeDaoH2Impl.save(reservationTime);
        reservationDaoH2Impl.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDaoH2Impl.findById(1L).get()));

        Assertions.assertThatThrownBy(() -> reservationTimeService.deleteById(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약된 id가 있어 지울 수 없습니다");
    }

    @Test
    @DisplayName("값이 null이면 예외가 발생한다")
    void createNotNull() {
        final ReservationDaoH2Impl reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDaoH2Impl,
                reservationDaoH2Impl);

        Assertions.assertThatThrownBy(() -> reservationTimeService.create(null))
                .isInstanceOf(NullPointerException.class);
    }
}
