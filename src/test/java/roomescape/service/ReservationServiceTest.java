package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.fixture.Fixture;
import roomescape.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeService reservationTimeService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("모든 예약들을 조회한다.")
    void getAllReservations() {
        // given
        ReservationTime reservationTime = Fixture.RESERVATION_TIME_1;
        Reservation reservation = Fixture.getReservation(reservationTime);
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        // when
        List<ReservationResponse> reservationResponses = reservationService.getAllReservations();

        // then
        assertThat(reservationResponses).containsExactly(ReservationResponse.from(reservation));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservation() {
        // given
        ReservationTime reservationTime = Fixture.RESERVATION_TIME_1;
        Reservation reservation = Fixture.getReservation(reservationTime);
        when(reservationTimeService.getReservationTimeByIdOrElseThrow(reservationTime.getId()))
                .thenReturn(reservationTime);
        when(reservationRepository.save(any()))
                .thenReturn(reservation);

        // when
        ReservationRequest reservationRequest = new ReservationRequest(
                reservation.getName(),
                reservation.getDate(),
                reservationTime.getId()
        );
        ReservationResponse savedReservation = reservationService.addReservation(reservationRequest);

        // then
        assertThat(savedReservation).isEqualTo(ReservationResponse.from(reservation));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservationById() {
        // given
        Long id = 1L;

        // when
        reservationService.deleteReservationById(id);

        // then
        verify(reservationRepository).deleteById(id);
    }
}
