package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.fixture.Fixture;
import roomescape.repository.ReservationRepository;

@SpringBootTest
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeService reservationTimeService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void getAllReservations() {
        // given
        ReservationTime reservationTime = Fixture.reservationTime(1L, 10, 30);
        Reservation reservation = Fixture.reservation(1L, "구름", 2024, 10, 25, reservationTime);
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        // when
        List<ReservationResponse> reservationResponses = reservationService.getAllReservations();

        // then
        assertThat(reservationResponses).containsExactly(ReservationResponse.from(reservation));
    }

    @Test
    void addReservation() {
        // given
        ReservationTime reservationTime = Fixture.reservationTime(1L, 10, 30);
        Reservation reservation = Fixture.reservation(1L, "구름", 2024, 10, 25, reservationTime);
        when(reservationTimeService.getReservationTimeByIdOrElseThrow(1L)).thenReturn(reservationTime);
        when(reservationRepository.save(any())).thenReturn(reservation);

        // when
        ReservationRequest reservationRequest = new ReservationRequest("구름", LocalDate.of(2024, 10, 25), 1L);
        ReservationResponse savedReservation = reservationService.addReservation(reservationRequest);

        // then
        assertThat(savedReservation).isEqualTo(ReservationResponse.from(reservation));
    }

    @Test
    void deleteReservationById() {
        // given
        Long id = 1L;

        // when
        reservationService.deleteReservationById(id);

        // then
        verify(reservationRepository).deleteById(id);
    }
}
