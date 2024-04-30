package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestConstant.TEST_START_AT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약시간을 저장한다.")
    @Test
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);

        // when
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        // then
        assertAll(
                () -> assertThat(savedReservationTime.getId()).isEqualTo(1L),
                () -> assertThat(savedReservationTime.getStartAt()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("예약시간을 조회한다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        // when
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        // then
        ReservationTime findReservationTime = reservationTimes.get(0);
        assertAll(
                () -> assertThat(reservationTimes).hasSize(1),
                () -> assertThat(findReservationTime.getId()).isEqualTo(savedReservationTime.getId()),
                () -> assertThat(findReservationTime.getStartAt()).isEqualTo(savedReservationTime.getStartAt())
        );
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        // when
        reservationTimeDao.delete(savedReservationTime.getId());

        // then
        assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
