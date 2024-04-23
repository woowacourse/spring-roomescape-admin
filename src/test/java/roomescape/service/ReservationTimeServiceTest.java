package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

    @AfterEach
    void afterEach() {
        reservationDao.deleteAll();
        reservationTimeDao.deleteAll();
    }

    @DisplayName("예약 시간의 예약이 존재하면 삭제할 수 없다.")
    @Test
    void deleteExistingReservation() {
        // given
        ReservationTime reservationTime = reservationTimeDao.save(new ReservationTime(LocalTime.parse("10:10")));
        Reservation reservation = reservationDao.save(
                new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));
        Long timeId = reservationTime.getId();
        Long reservationId = reservation.getId();

        // when & given
        assertThatThrownBy(() -> reservationTimeService.delete(timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 ID는 삭제할 수 없다.")
    @Test
    void deleteNonExisting() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = reservationTimeDao.save(new ReservationTime(LocalTime.parse("10:10")));

        // when
        reservationTimeDao.delete(reservationTime);

        // then
        Assertions.assertThat(reservationTimeDao.findById(reservationTime.getId())).isEmpty();
    }
}
