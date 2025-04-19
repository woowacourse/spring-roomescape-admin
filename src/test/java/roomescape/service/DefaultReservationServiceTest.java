package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import roomescape.reservation.application.DefaultReservationService;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class DefaultReservationServiceTest {

    @Autowired
    private DefaultReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("존재하지 않는 아이디를 삭제시 예외 발생")
    void deleteException() {
        // given
        final long invalidId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.delete(invalidId)).
                isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("DataAccessException 발생 시 삭제 실패를 알린다")
    void deleteUnknownDataAccessException() {
        // given
        final long invalidId = 1L;
        final Reservation fakeReservation = Reservation.of(invalidId, "테스트", LocalDateTime.now());

        reservationService = new DefaultReservationService(reservationRepository);

        // 저장된 상황
        Mockito.when(reservationRepository.findById(invalidId))
                .thenReturn(Optional.of(fakeReservation));

        // 그런데 문제 발생 (커넥션 등)
        Mockito.doThrow(new DataAccessException("DB Unknown Error") {
                })
                .when(reservationRepository).deleteById(invalidId);

        // when
        // then
        assertThatThrownBy(() -> reservationService.delete(invalidId))
                .isInstanceOf(DataAccessException.class);
    }
}
