package roomescape.service.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @DisplayName("모든 예약 시간 정보를 응답 dto로 반환한다.")
    @Test
    void find_all_reservation_times() {
        ReservationTime time1 = new ReservationTime(1L, "11:30");
        ReservationTime time2 = new ReservationTime(2L, "23:59");
        List<ReservationTime> reservationTimes = List.of(
                time1,
                time2
        );
        List<ReservationTimeResponseDto> responseDtos = List.of(
                new ReservationTimeResponseDto(time1),
                new ReservationTimeResponseDto(time2)
        );

        when(reservationTimeRepository.findAllReservationTimes()).thenReturn(reservationTimes);

        List<ReservationTimeResponseDto> results = reservationTimeService.findAllReservationTimes();

        assertAll(
                () -> assertThat(results.size()).isEqualTo(responseDtos.size()),
                () -> assertThat(results.get(0).getId()).isEqualTo(responseDtos.get(0).getId())
        );
    }

    @DisplayName("예약 시간을 저장하고 예약 시간 응답 dto를 반환한다.")
    @Test
    void create_reservation_time_test() {
        ReservationTimeRequestDto requestDto = new ReservationTimeRequestDto("11:30");
        ReservationTime reservationTime = new ReservationTime(1L, "11:30");
        ReservationTimeResponseDto responseDto = new ReservationTimeResponseDto(reservationTime);

        when(reservationTimeRepository.insertReservationTime(requestDto.toReservationTime())).thenReturn(
                reservationTime);

        ReservationTimeResponseDto result = reservationTimeService.createReservationTime(requestDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(responseDto.getId()),
                () -> assertThat(result.getStartAt()).isEqualTo(responseDto.getStartAt())
        );
    }
}
