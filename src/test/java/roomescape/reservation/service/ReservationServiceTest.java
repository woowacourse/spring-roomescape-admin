package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.reservation.dto.ReservationSaveRequest;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.repository.TimeRepository;

@DisplayName("예약 서비스")
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private ReservationService reservationService;

    @DisplayName("존재하지 않는 reservation id일 경우 예외가 발생한다.")
    @Test
    void findByIdExceptionByNotExistReservationIdTest() {
        // given
        Long reservationId = 1L;

        doReturn(Optional.empty()).when(reservationRepository)
                .findById(reservationId);

        // when & then
        assertThatThrownBy(() -> reservationService.findById(reservationId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 time id일 경우 예외가 발생한다.")
    @Test
    void findByIdExceptionByNotExistTimeIdTest() {
        // given
        Long timeId = 1L;
        ReservationSaveRequest reservationSaveRequest = new ReservationSaveRequest("브라운", LocalDate.parse("2024-08-05"), timeId);

        doReturn(Optional.empty()).when(timeRepository)
                .findById(timeId);

        // when & then
        assertThatThrownBy(() -> reservationService.save(reservationSaveRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
