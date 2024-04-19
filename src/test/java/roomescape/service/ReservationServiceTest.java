package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @DisplayName("시간이 겹치는 예약이 존재하지 않는 경우 예약에 성공한다")
    @Test
    void reservationSaveSuccessTest() {
        Reservation reservation = new Reservation("리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        when(reservationRepository.isAnyReservationConflictWith(reservation)).thenReturn(false);

        assertThatCode(() -> reservationService.saveReservation(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간이 겹치는 예약이 존재할 경우 예약에 실패한다")
    @Test
    void reservationSaveFailByTimeConflictTest() {
        Reservation reservation = new Reservation("리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        when(reservationRepository.isAnyReservationConflictWith(reservation)).thenReturn(true);

        assertThatThrownBy(() -> reservationService.saveReservation(reservation))
                .isInstanceOf(IllegalStateException.class);
    }
}
