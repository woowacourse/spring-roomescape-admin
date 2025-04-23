package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.resetvationTime.InMemoryReservationTimeDao;
import roomescape.dao.resetvationTime.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

class InMemoryReservationTimeDaoTest {

    private final ReservationTimeDao reservationTimeDao = new InMemoryReservationTimeDao(new ArrayList<>());

    @DisplayName("예약 시간을 조회한다.")
    @Test
    void getTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when

        // then
        assertThat(reservationTimeDao.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // then
        assertThat(reservationTimeDao.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when
        List<ReservationTime> reservationTimes = this.reservationTimeDao.findAll();
        ReservationTime reservationTime = reservationTimes.getFirst();
        this.reservationTimeDao.delete(reservationTime.getId());

        // then
        assertThat(this.reservationTimeDao.findAll().size()).isEqualTo(0);
    }

}
