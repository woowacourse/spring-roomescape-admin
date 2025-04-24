package roomescape.reservationtime.unit.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;
import roomescape.reservationtime.unit.repository.FakeReservationTimeRepository;

class ReservationTimeServiceTest {

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void beforeEach() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void getReservationTimes() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);

        // when
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();

        // then
        ReservationTime expected = new ReservationTime(1L, LocalTime.of(10, 0));
        assertThat(reservationTimes.size()).isEqualTo(1);
        assertThat(reservationTimes.getFirst()).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간을 저장한다.")
    void createReservationTime() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(
                LocalTime.of(10, 0)
        );

        // when
        ReservationTime created = reservationTimeService.createReservationTime(request);

        // then
        ReservationTime expected = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        assertThat(created).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간이 중복되면 예외가 발생한다.")
    void createReservationTime_Duplicate() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);
        ReservationTimeRequest request = new ReservationTimeRequest(
                LocalTime.of(10, 0)
        );

        // when & then
        assertThatThrownBy(() -> reservationTimeService.createReservationTime(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 시간입니다.");
    }

    @Test
    @DisplayName("id로 시간을 삭제한다.")
    void deleteReservationTime() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);

        // when
        reservationTimeService.deleteReservationTime(1L);

        // then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
