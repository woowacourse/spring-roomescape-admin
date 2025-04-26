package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.usecase.reservation.AddReservationService;
import roomescape.usecase.reservation.ReservationInput;
import roomescape.usecase.reservation.ReservationRepository;
import roomescape.usecase.reservationTime.ReservationTimeRepository;

class AddReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private AddReservationService addReservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        reservationTimeRepository = mock(ReservationTimeRepository.class);
        addReservationService = new AddReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    void 과거_시간으로_예약하려하면_예외가_발생한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(0, 0));
        ReservationInput input = new ReservationInput(LocalDate.now().minusDays(1), "브라운", 1L);

        when(reservationTimeRepository.getReservationTime(1L)).thenReturn(reservationTime);

        // when & then
        assertThatThrownBy(() -> addReservationService.addReservation(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 시각 이후의 예약만 가능합니다.");
    }
}
