package roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.request.ReservationTimeCreateRequest;

class InMemoryReservationTimesTest {

    private final ReservationTimes reservationTimes = new InMemoryReservationTimes(new ArrayList<>());

    @DisplayName("예약 시간을 조회한다.")
    @Test
    void getTest() {

        // given
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when

        // then
        assertThat(reservationTimes.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // then
        assertThat(reservationTimes.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when
        List<ReservationTime> reservationTimes = this.reservationTimes.findAll();
        ReservationTime reservationTime = reservationTimes.getFirst();
        this.reservationTimes.delete(reservationTime.getId());

        // then
        assertThat(this.reservationTimes.findAll().size()).isEqualTo(0);
    }

}
