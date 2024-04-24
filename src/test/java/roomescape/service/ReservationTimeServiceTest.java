package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDAO;

class ReservationTimeServiceTest {
    ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeDAO());

    @DisplayName("모든 예약 시간을 반환한다")
    @Test
    void should_return_all_reservation_times() {
        List<ReservationTime> reservationTimes = reservationTimeService.findAllReservationTimes();
        assertThat(reservationTimes).hasSize(2);
    }

    @DisplayName("아이디에 해당하는 예약 시간을 반환한다.")
    @Test
    void should_get_reservation_time() {
        ReservationTime reservationTime = reservationTimeService.findReservationTime(2);
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(11, 0));
    }

    @DisplayName("예약 시간을 추가한다")
    @Test
    void should_add_reservation_times() {
        ReservationTime reservationTime
                = reservationTimeService.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(3);
    }

    @DisplayName("예약 시간을 삭제한다")
    @Test
    void should_remove_reservation_times() {
        reservationTimeService.removeReservationTime(1);
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(1);
    }

    class FakeReservationTimeDAO implements ReservationTimeDAO {

        private List<ReservationTime> reservationTimes = new ArrayList<>(List.of(
                new ReservationTime(1, LocalTime.of(10, 0)),
                new ReservationTime(2, LocalTime.of(11, 0))
        ));


        @Override
        public List<ReservationTime> selectAllReservationTimes() {
            return reservationTimes;
        }

        @Override
        public ReservationTime selectReservationById(long id) {
            return reservationTimes.stream()
                    .filter(reservationTime -> reservationTime.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("해당하는 아이디가 없습니다."));
        }

        @Override
        public ReservationTime insertReservationTime(ReservationTime reservationTime) {
            reservationTimes.add(reservationTime);
            reservationTime.setId(3);
            return reservationTime;
        }

        @Override
        public void deleteReservationTime(long id) {
            ReservationTime findReservationTime = reservationTimes.stream()
                    .filter(reservationTime -> reservationTime.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("해당하는 아이디가 없습니다."));

            reservationTimes.remove(findReservationTime);
        }
    }
}
