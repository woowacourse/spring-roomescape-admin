package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.reservation.repository.stub.FakeReservationTimeRepository;

class ReservationTimeServiceTest {

    ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();
    ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    @DisplayName("예약 시간들을 불러온다.")
    @Test
    void getTimes() {
        // given
        reservationTimeService.addTime(new ReservationTimeRequest(LocalTime.of(12, 1)));

        // when
        List<ReservationTimeResponse> times = reservationTimeService.getTimes();

        // then
        assertThat(times).hasSize(1);
    }

    @DisplayName("예약 시간을 추가한다.")
    @Test
    void addTime() {
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.of(12, 1));

        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.addTime(reservationTimeRequest);

        // then
        assertThat(reservationTimeResponse)
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 1));
    }

    @DisplayName("id에 해당하는 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        // given
        long id = reservationTimeService.addTime(new ReservationTimeRequest(LocalTime.of(12, 1))).id();

        // when
        boolean isDeleted = reservationTimeService.deleteTimeById(id);

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("id에 해당하는 예약 시간을 찾는다.")
    @Test
    void findTimeById() {
        // given
        long id = reservationTimeService.addTime(new ReservationTimeRequest(LocalTime.of(12, 1))).id();

        // when
        ReservationTime reservationTime = reservationTimeService.findTimeById(id);

        // then
        assertThat(reservationTime)
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 1));
    }
}