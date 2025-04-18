package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {
    private final Reservations reservations = new Reservations();

    @Test
    @DisplayName("같은 날짜, 같은 시각에 이미 예약이 존재하는 경우, 재생성할 수 없다.")
    void duplicateReservation() {
        // given
        LocalDate date = LocalDate.of(2025, 1, 2);
        LocalTime time = LocalTime.of(12, 0);
        reservations.save(new Reservation("test", date, time));

        // when & then
        assertThatThrownBy(() -> {
            reservations.save(new Reservation("test2", date, time));
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
