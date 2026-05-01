package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("새로운 예약 시간을 생성한다")
    void create() {
        // given
        LocalTime startTime = LocalTime.of(14, 0);
        ReservationTimeRequest request = new ReservationTimeRequest(startTime);
        given(reservationTimeRepository.create(any(ReservationTime.class))).willReturn(1L);

        // when
        ReservationTimeResponse response = reservationTimeService.create(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStartAt()).isEqualTo(startTime);
        verify(reservationTimeRepository).create(any(ReservationTime.class));
    }

    @Test
    @DisplayName("모든 예약 시간을 조회하여 DTO로 변환한다")
    void findAll() {
        // given
        List<ReservationTime> mockTimes = List.of(
                new ReservationTime(1L, LocalTime.of(10, 0)),
                new ReservationTime(2L, LocalTime.of(13, 0))
        );
        given(reservationTimeRepository.findAll()).willReturn(mockTimes);

        // when
        List<ReservationTimeResponse> responses = reservationTimeService.findAll();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getStartAt()).isEqualTo(LocalTime.of(10, 0));
        verify(reservationTimeRepository).findAll();
    }

    @Test
    @DisplayName("ID를 이용해 예약 시간을 삭제한다")
    void delete() {
        // given
        Long targetId = 1L;
        given(reservationTimeRepository.delete(targetId)).willReturn(1);

        // when
        int result = reservationTimeService.delete(targetId);

        // then
        assertThat(result).isEqualTo(1);
        verify(reservationTimeRepository).delete(targetId);
    }
}
