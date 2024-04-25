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
import roomescape.db.ReservationTimeDao;
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
        final ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
        final ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao, reservationDao);
        final ReservationTime reservationTime = ReservationTime.from(LocalTime.now());

        reservationTimeDao.save(reservationTime);
        reservationDao.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDao.findById(1L).get()));

        Assertions.assertThatThrownBy(() -> reservationTimeService.deleteById(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약된 id가 있어 지울 수 없습니다");
    }

    @Test
    @DisplayName("값이 null이면 예외가 발생한다")
    void createNotNull() {
        final ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
        final ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao, reservationDao);

        Assertions.assertThatThrownBy(() -> reservationTimeService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("null이 될 수 없습니다.");
    }
}
