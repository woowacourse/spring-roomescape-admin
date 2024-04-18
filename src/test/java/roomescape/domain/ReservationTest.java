package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTest {
    @Test
    @DisplayName("날짜를 기준으로 비교를 잘 하는지 확인.")
    void compareTo() {
        Reservation first = new Reservation(1L, "폴라", LocalDateTime.of(1999, 12, 1, 16, 30));
        Reservation second = new Reservation(2L, "로빈", LocalDateTime.of(1998, 1, 8, 10, 30));
        int compareTo = first.compareTo(second);

        Assertions.assertThat(compareTo)
                .isGreaterThan(0);
    }

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
