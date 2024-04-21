package roomescape.service.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.service.reservation.dto.ReservationRequestDto;
import roomescape.service.reservation.dto.ReservationResponseDto;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @DisplayName("모든 예약 정보를 dto 리스트로 반환한다.")
    @Test
    void find_all_reservations_test() {
        Reservation reservation1 = new Reservation(1L, "안돌", "2024-09-08", 1L, "00:00");
        Reservation reservation2 = new Reservation(2L, "재즈", "2024-11-30", 1L, "00:00");
        List<Reservation> reservations = List.of(
                reservation1,
                reservation2
        );
        List<ReservationResponseDto> responseDtos = List.of(
                new ReservationResponseDto(reservation1),
                new ReservationResponseDto(reservation2)
        );

        when(reservationRepository.findAllReservations()).thenReturn(reservations);

        List<ReservationResponseDto> results = reservationService.findAllReservations();

        assertAll(
                () -> assertThat(results.size()).isEqualTo(responseDtos.size()),
                () -> assertThat(results.get(0).getId()).isEqualTo(responseDtos.get(0).getId())
        );
    }

    @DisplayName("예약을 저장하고 예약 응답 dto를 반환한다.")
    @Test
    void create_reservation_test() {
        ReservationRequestDto requestDto = new ReservationRequestDto("재즈", "2024-04-21", 1L);
        Reservation reservation = new Reservation(1L, "재즈", "2024-04-21", 1L, "15:30");
        ReservationResponseDto responseDto = new ReservationResponseDto(reservation);

        when(reservationRepository.insertReservation(requestDto.toReservation())).thenReturn(reservation);

        ReservationResponseDto result = reservationService.createReservation(requestDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(responseDto.getId()),
                () -> assertThat(result.getName()).isEqualTo(responseDto.getName()),
                () -> assertThat(result.getDate()).isEqualTo(responseDto.getDate()),
                () -> assertThat(result.getTime().getId()).isEqualTo(responseDto.getTime().getId())
        );
    }
}
