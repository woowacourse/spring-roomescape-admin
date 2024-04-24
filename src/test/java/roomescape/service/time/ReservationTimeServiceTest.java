package roomescape.service.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.ReservationTime;
import roomescape.repository.time.ReservationTimeRepository;
import roomescape.service.time.dto.ReservationTimeRequestDto;
import roomescape.service.time.dto.ReservationTimeResponseDto;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    private ReservationTimeService reservationTimeService;

    @DisplayName("모든 예약 시간 정보 조회 및 의존 객체 상호작용 테스트")
    @Test
    void find_all_reservation_times() {
        ReservationTime time1 = new ReservationTime(1L, "11:30");
        ReservationTime time2 = new ReservationTime(2L, "23:59");
        List<ReservationTime> reservationTimes = List.of(time1, time2);
        given(reservationTimeRepository.findAllReservationTimes()).willReturn(reservationTimes);

        List<ReservationTimeResponseDto> results = reservationTimeService.findAllReservationTimes();
        verify(reservationTimeRepository, times(1)).findAllReservationTimes();

        List<ReservationTimeResponseDto> responseDtos = List.of(
                new ReservationTimeResponseDto(time1),
                new ReservationTimeResponseDto(time2)
        );
        assertAll(
                () -> assertThat(results.size()).isEqualTo(responseDtos.size()),
                () -> assertThat(results.get(0).getId()).isEqualTo(responseDtos.get(0).getId())
        );
    }

    @DisplayName("예약 시간 저장 및 의존 객체 상호작용 테스트")
    @Test
    void create_reservation_time_test() {
        ReservationTimeRequestDto requestDto = new ReservationTimeRequestDto("11:30");
        ReservationTime reservationTime = new ReservationTime(1L, "11:30");
        given(reservationTimeRepository.insertReservationTime(requestDto.toReservationTime())).willReturn(
                reservationTime);

        ReservationTimeResponseDto result = reservationTimeService.createReservationTime(requestDto);
        verify(reservationTimeRepository, times(1)).insertReservationTime(requestDto.toReservationTime());

        ReservationTimeResponseDto responseDto = new ReservationTimeResponseDto(reservationTime);
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(responseDto.getId()),
                () -> assertThat(result.getStartAt()).isEqualTo(responseDto.getStartAt())
        );
    }
}
