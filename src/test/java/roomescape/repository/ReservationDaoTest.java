package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @AfterEach
    void after() {
        reservationDao.deleteAll();
        reservationTimeDao.deleteAll();
    }

    @DisplayName("예약 시간이 존재하는지 확인한다.")
    @Test
    void existFalse() {
        Boolean result = reservationDao.existByTimeId(1L);

        assertThat(result).isFalse();
    }

    @DisplayName("예약 시간이 존재하는지 확인한다.")
    @Test
    void existTrue() {
        ReservationTime reservationTime = reservationTimeDao.save(new ReservationTime(LocalTime.parse("10:10")));
        reservationDao.save(new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));

        Boolean result = reservationDao.existByTimeId(reservationTime.getId());

        // then
        assertThat(result).isTrue();
    }
}
