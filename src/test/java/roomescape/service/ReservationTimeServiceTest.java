package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void afterEach() {
        reservationDao.deleteAll();
        reservationTimeDao.deleteAll();
    }

    @DisplayName("예약 시간을 삭제하면 해당 예약 시간의 예약이 전부 삭제된다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = reservationTimeDao.save(new ReservationTime(LocalTime.parse("10:10")));
        Reservation reservation = reservationDao.save(
                new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));
        Long timeId = reservationTime.getId();
        Long reservationId = reservation.getId();

        // when
        reservationTimeService.delete(timeId);

        // then
        assertAll(
                () -> assertThat(reservationTimeDao.findById(timeId)).isEmpty(),
                () -> assertThat(reservationDao.findById(reservationId)).isEmpty()
        );
    }

    @DisplayName("존재하지 않는 ID는 삭제할 수 없다.")
    @Test
    void deleteNonExisting() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
