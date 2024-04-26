package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("Reservation 객체의 동등성을 따질 때는 id만 확인한다.")
    void testEquals() {
        Reservation reservation1 = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2024, 4, 24),
                null
        );
        Reservation reservation2 = new Reservation(
                1L,
                "솔라",
                LocalDate.of(2024, 3, 22),
                null
        );

        assertThat(reservation1).isEqualTo(reservation2);
    }
}
