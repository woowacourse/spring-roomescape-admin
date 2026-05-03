package roomescape.reservationtime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;

@SpringBootTest
@Transactional
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("영업 시간 내의 예약 시간을 저장한다")
    void 영업_시간_내_시간_저장() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(14, 0));

        // when
        ReservationTimeResponse response = reservationTimeService.saveReservationTime(request);

        // then
        assertThat(response.startAt()).isEqualTo(LocalTime.of(14, 0));
        assertThat(response.id()).isNotNull();
    }

    @Test
    @DisplayName("영업 시간(10시 ~ 22시) 이전의 시간을 저장하면 예외가 발생한다")
    void 영업_시간_전_저장_예외() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(9, 59));

        // when & then
        assertThatThrownBy(() -> reservationTimeService.saveReservationTime(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("영업 시간(10시 ~ 22시) 이후의 시간을 저장하면 예외가 발생한다")
    void 영업_시간_후_저장_예외() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(22, 1));

        // when & then
        assertThatThrownBy(() -> reservationTimeService.saveReservationTime(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("영업 시간 내의 예약 시간을 조회한다")
    void 영업_시간_내_시간_조회() {
        // given
        reservationTimeService.saveReservationTime(new ReservationTimeRequest(LocalTime.of(14, 0)));
        reservationTimeService.saveReservationTime(new ReservationTimeRequest(LocalTime.of(15, 0)));

        // when
        List<ReservationTimeResponse> times = reservationTimeService.findAllReservationTimes();

        // then
        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다")
    void 예약_시간_삭제() {
        // given
        ReservationTimeResponse saved = reservationTimeService.saveReservationTime(new ReservationTimeRequest(LocalTime.of(14, 0)));

        // when
        reservationTimeService.deleteById(saved.id());

        // then
        List<ReservationTimeResponse> times = reservationTimeService.findAllReservationTimes();
        assertThat(times).isEmpty();
    }
}
