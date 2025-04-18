package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.dto.ReservationRequest;

@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationService;

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
    @DisplayName("날짜와 시간이 모두 중복되면 예외가 발생한다.")
    void whenDuplicateDateAndTimeThrowException(){
        // given
        ReservationRequest existedRequest = new ReservationRequest("lemon", LocalDate.of(2025, 4, 18), LocalTime.of(16, 53));
        ReservationRequest newRequest = new ReservationRequest("lemon", LocalDate.of(2025, 4, 18), LocalTime.of(16, 53));
        // when
        reservationService.createReservation(existedRequest);
        // then
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(newRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
