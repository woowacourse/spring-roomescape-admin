package roomescape.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.ReservationTimeRepository;
import roomescape.dto.reservationtime.ReservationTimeRequest;
import roomescape.support.FakeReservationTimeRepository;

class ReservationTimeServiceTest {
    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    @Test
    void 예약_시간을_성공적으로_등록한다() {
        LocalTime startAt = LocalTime.of(13, 0);
        ReservationTimeRequest request = new ReservationTimeRequest(startAt);

        ReservationTime reservationTime = reservationTimeService.register(request);

        assertThat(reservationTime.getStartAt()).isEqualTo(startAt);
    }

    @Test
    void 중복된_예약_시간이_있으면_등록을_실패한다() {
        LocalTime startAt = LocalTime.of(13, 0);
        ReservationTimeRequest request = new ReservationTimeRequest(startAt);
        reservationTimeService.register(request);

        ReservationTimeRequest duplicatedRequest = new ReservationTimeRequest(startAt);

        assertThatThrownBy(() -> reservationTimeService.register(duplicatedRequest))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 예약 시간입니다.");
    }

    @Test
    void 예약_시간을_삭제한다() {
        LocalTime startAt = LocalTime.of(13, 0);
        ReservationTimeRequest request = new ReservationTimeRequest(startAt);
        ReservationTime reservationTime = reservationTimeService.register(request);

        reservationTimeService.delete(reservationTime.getId());

        Optional<ReservationTime> optionalReservationTime = reservationTimeRepository.findById(reservationTime.getId());
        assertThat(optionalReservationTime).isEmpty();
    }

    @Test
    void 존재하지_않는_예약_시간을_삭제하면_예외가_발생한다() {
        long nonExistentId = 0L;
        assertThatThrownBy(() -> reservationTimeService.delete(nonExistentId))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 예약 시간입니다.");
    }
}
