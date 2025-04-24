package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.exception.reservationTime.ReservationTimeNotFoundException;
import roomescape.repository.ReservationTimeRepository;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {
    @Mock
    private ReservationTimeRepository timeRepository;

    @InjectMocks
    private ReservationTimeService timeService;

    @DisplayName("예약 시간을 생성한다")
    @Test
    void
    create() {
        // given
        LocalTime now = LocalTime.now();
        ReservationTimeRequest request = new ReservationTimeRequest(now);
        Long mockId = 1L;
        ReservationTime savedTime = new ReservationTime(mockId, now);

        when(timeRepository.add(any(ReservationTime.class))).thenReturn(savedTime);

        // when
        ReservationTimeResponse result = timeService.create(request);

        // then
        assertThat(result.getStartAt()).isEqualTo(now);
    }

    @DisplayName("전체 예약 시간을 조회한다")
    @Test
    void getAll() {
        // given
        LocalTime time1 = LocalTime.now();
        LocalTime time2 = LocalTime.now().plusHours(1);

        ReservationTime mockTime1 = new ReservationTime(1L, time1);
        ReservationTime mockTime2 = new ReservationTime(2L, time2);

        when(timeRepository.findAll()).thenReturn(List.of(mockTime1, mockTime2));

        // when
        List<ReservationTimeResponse> responses = timeService.getAll();

        // then
        assertThat(responses).hasSize(2);
    }

    @DisplayName("존재하지 않는 id의 예약시간 삭제시 예외를 발생시킨다")
    @Test
    void delete() {
        // given
        Long id = 99L;
        when(timeRepository.deleteBy(id)).thenReturn(0);

        // when // then
        assertThatThrownBy(() -> timeService.deleteBy(id))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }

    @DisplayName("존재하지 않는 id의 예약시간 조회시 예외를 발생시킨다")
    @Test
    void get() {
        // given
        Long id = 99L;
        when(timeRepository.findBy(id)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> timeService.getBy(id))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }
}
