package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationsTest {
    @Autowired
    private Reservations reservations;

    @Test
    @DisplayName("같은 날짜, 같은 시각에 이미 예약이 존재하는 경우, 재생성할 수 없다.")
    void duplicateReservation() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 2, 12, 0);
        reservations.save(Reservation.of("test", dateTime));

        // when & then
        assertThatThrownBy(() -> {
            reservations.save(Reservation.of("test2", dateTime));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    void deleteNotExistedReservation() {
        // given

        // when & then
        assertThatThrownBy(() -> {
            reservations.deleteById(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
