package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("id가 같으면 같은 hash 값을 갖고 동일한 객체이다.")
    void hash_equals_override_test() {
        ReservationTime time1 = new ReservationTime(1L, LocalTime.of(12, 4));
        ReservationTime time2 = new ReservationTime(1L, LocalTime.of(20, 9));

        assertAll(
                () -> assertThat(time1.hashCode()).isEqualTo(time2.hashCode()),
                () -> assertThat(time1).isEqualTo(time2)
        );
    }
}
