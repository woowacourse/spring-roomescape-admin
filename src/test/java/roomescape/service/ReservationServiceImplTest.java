package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("존재하지 않는 아이디를 삭제시 예외 발생")
    void deleteException() {
        // given
        long invalidId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.delete(invalidId)).
                isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("DataAccessException 발생 시 삭제 실패를 알린다")
    void deleteUnknownDataAccessException() {
        // given
        long invalidId = 1;
        Reservation fakeReservation = new Reservation(1L, "테스트", LocalDate.now(), LocalTime.now());

        reservationService = new ReservationServiceImpl(reservationRepository);

        // 저장된 상황
        Mockito.when(reservationRepository.findById(invalidId))
                .thenReturn(Optional.of(fakeReservation));

        // 그런데 문제 발생 (커넥션 등)
        Mockito.doThrow(new DataAccessException("DB Unknown Error") {})
                .when(reservationRepository).deleteById(invalidId);

        // when
        // then
        assertThatThrownBy(() -> reservationService.delete(invalidId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("삭제에 실패했습니다");
    }
}
