package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;

class ReservationTimeServiceTest {

    @DisplayName("모든 예약 시간을 찾습니다")
    @Test
    void findAllReservationTime() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao(
                Arrays.asList(new ReservationTime(1L, LocalTime.of(10, 0)))
        ));
        int expectedSize = 1;

        int actualSize = reservationTimeService.findAllReservationTime().size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @DisplayName("예약시간을 추가하고 저장된 예약시간을 반환합니다.")
    @Test
    void should_add_reservation_time() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());
        ReservationTime expectedReservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        ReservationTime actualReservationTime = reservationTimeService.addReservationTime(new ReservationTimeAddRequest(
                LocalTime.of(10, 0)
        ));

        assertThat(actualReservationTime).isEqualTo(expectedReservationTime);
    }

    @DisplayName("원하는 id의 예약시간을 삭제하면 true를 반환합니다.")
    @Test
    void should_true_when_remove_reservation_time_with_exist_id() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao(
                Arrays.asList(new ReservationTime(1L, LocalTime.of(10, 0)))
        ));

        assertThat(reservationTimeService.removeReservationTime(1L)).isTrue();
    }

    @DisplayName("없는 id의 예약시간을 삭제하면 false를 반환합니다.")
    @Test
    void should_true_when_remove_reservation_time_with_non_exist_id() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());

        assertThat(reservationTimeService.removeReservationTime(1L)).isFalse();
    }
}
