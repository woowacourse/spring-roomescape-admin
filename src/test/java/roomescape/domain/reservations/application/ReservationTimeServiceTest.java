package roomescape.domain.reservations.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservations.FakeReservationTimeRepository;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationTimeRepository;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

class ReservationTimeServiceTest {

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    private ReservationTimeRequest createReservationTimeRequest() {
        return new ReservationTimeRequest(LocalTime.of(10, 0));
    }

    private ReservationTimeResponse saveTime(LocalTime startAt) {
        return reservationTimeService.saveTime(new ReservationTimeRequest(startAt));
    }

    @Test
    @DisplayName("예약 시간을 저장한다")
    void saveTime() {
        // given
        ReservationTimeRequest request = createReservationTimeRequest();

        // when
        ReservationTimeResponse savedTime = reservationTimeService.saveTime(request);

        // then
        assertThat(savedTime.id()).isNotNull();
        assertThat(savedTime.startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약 시간 저장 요청이 null이면 예외가 발생한다")
    void saveTimeWithNullRequest() {
        // when & then
        assertThatThrownBy(() -> reservationTimeService.saveTime(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간이 null이면 예외가 발생한다")
    void saveTimeWithNullStartAt() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(null);

        // when & then
        assertThatThrownBy(() -> reservationTimeService.saveTime(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간 목록을 조회한다")
    void getTimes() {
        // given
        ReservationTimeResponse firstTime = saveTime(LocalTime.of(10, 0));
        ReservationTimeResponse secondTime = saveTime(LocalTime.of(11, 0));

        // when
        List<ReservationTime> times = reservationTimeService.getTimes();

        // then
        assertThat(times).hasSize(2);
        assertThat(times)
                .extracting(ReservationTime::getId)
                .containsExactlyInAnyOrder(firstTime.id(), secondTime.id());
        assertThat(times)
                .extracting(ReservationTime::getStartAt)
                .containsExactlyInAnyOrder(LocalTime.of(10, 0), LocalTime.of(11, 0));
    }

    @Test
    @DisplayName("예약 시간이 없으면 빈 목록을 조회한다")
    void getTimesWhenEmpty() {
        // when
        List<ReservationTime> times = reservationTimeService.getTimes();

        // then
        assertThat(times).isEmpty();
    }

    @Test
    @DisplayName("예약 시간을 삭제한다")
    void deleteTime() {
        // given
        ReservationTimeResponse savedTime = saveTime(LocalTime.of(10, 0));

        // when
        reservationTimeService.deleteTime(savedTime.id());

        // then
        assertThat(reservationTimeService.getTimes()).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약 시간을 삭제해도 예외가 발생하지 않는다")
    void deleteNotFoundTime() {
        // when
        reservationTimeService.deleteTime(999L);

        // then
        assertThat(reservationTimeService.getTimes()).isEmpty();
    }

    @Test
    @DisplayName("예약 시간 id가 null이면 삭제할 때 예외가 발생한다")
    void deleteTimeWithNullId() {
        // when & then
        assertThatThrownBy(() -> reservationTimeService.deleteTime(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
