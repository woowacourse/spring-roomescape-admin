package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @DisplayName("존재하지 않는 id일 경우 예외가 발생한다.")
    @Test
    void findByIdExceptionTest() {
        // given
        Long id = 1L;

        doReturn(Optional.empty()).when(reservationRepository).findById(id);

        // when & then
        assertThatThrownBy(() -> reservationService.findById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
