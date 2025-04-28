package roomescape.reservation_time.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation_time.domain.ReservationTime;
import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.domain.ReservationTimeRepository;
import roomescape.reservation_time.ui.dto.ReservationTimeRequestDto;
import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class DefaultReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약 시간을 전체 조회할 수 있다")
    void getAllReservationTimes() {
        // given
        reservationTimeRepository.save(ReservationTime.of(
                ReservationTimeId.unassigned(), LocalTime.of(10, 0)));
        reservationTimeRepository.save(ReservationTime.of(
                ReservationTimeId.unassigned(), LocalTime.of(11, 0)));

        // when
        final List<ReservationTimeResponseDto> times = reservationTimeService.getAll();

        // then
        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간을 생성할 수 있다")
    void createReservationTime() {
        // given
        final ReservationTimeRequestDto requestDto = new ReservationTimeRequestDto(LocalTime.of(12, 30));

        // when
        final ReservationTimeResponseDto responseDto = reservationTimeService.create(requestDto);

        // then
        assertThat(responseDto.startAt()).isEqualTo(LocalTime.of(12, 30));
        assertThat(reservationTimeRepository.findById(
                ReservationTimeId.from(responseDto.id()))).isPresent();
    }

    @Test
    @DisplayName("예약 시간을 삭제할 수 있다")
    void deleteReservationTime() {
        // given
        final ReservationTime saved =
                reservationTimeRepository.save(ReservationTime.of(
                        ReservationTimeId.unassigned(), LocalTime.of(14, 0)));
        final ReservationTimeId id = saved.getId();

        // when
        reservationTimeService.delete(id);

        // then
        assertThat(reservationTimeRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약 시간을 삭제하려 하면 예외가 발생한다")
    void deleteNonExistentReservationTime() {
        // given
        final ReservationTimeId id = ReservationTimeId.from(-1L);

        // when
        // then
        assertThatThrownBy(() -> reservationTimeService.delete(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
