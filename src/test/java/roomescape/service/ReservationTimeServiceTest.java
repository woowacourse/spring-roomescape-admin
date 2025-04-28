package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationTimeDao;
import roomescape.model.ReservationTime;

public class ReservationTimeServiceTest {

    @Test
    void 예약_시간_추가_성공_테스트() {
        //given
        FakeReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao);

        //when
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.parse("10:10"));
        ReservationTime addedResrvationTime = reservationTimeService.addReservation(reservationTime);

        //then
        assertThat(addedResrvationTime.getTime().toString()).isEqualTo("10:10");
        assertThat(addedResrvationTime.getId()).isEqualTo(1);

    }

    @Test
    void 예약_시간_삭제_성공_테스트() {
        //given
        FakeReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao);

        //when
        int effectedCount = reservationTimeService.deleteTimeReservationById(1L);

        //then
        assertThat(effectedCount).isEqualTo(1);

    }

    static class FakeReservationTimeDao implements ReservationTimeDao {

        @Override
        public ReservationTime addReservation(ReservationTime reservationTime) {
            return new ReservationTime(1L, LocalTime.parse("10:10"));
        }

        @Override
        public List<ReservationTime> getTimeReservations() {
            return List.of();
        }

        @Override
        public int deleteTimeReservation(Long id) {
            return 1;
        }

        @Override
        public ReservationTime findTimeById(Long reservationTimeId) {
            return null;
        }
    }
}
