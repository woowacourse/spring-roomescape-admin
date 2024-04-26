package roomescape.domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @Test
    @DisplayName("전달된 id와 같은 값의 id인지 확인.")
    void hasSameId() {
        Reservation reservation = new Reservation(1L, "폴라", LocalDateTime.of(1999, 12, 1, 16, 30));

        assertAll(
                () -> Assertions.assertThat(reservation.hasSameId(2L)).isFalse(),
                () -> Assertions.assertThat(reservation.hasSameId(1L)).isTrue()
        );
    }
}
