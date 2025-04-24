package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("ReservationTime에 id를 삽입한 객체를 만들 수 있다")
    void withId() {
        // given
        ReservationTime time = new ReservationTime(null, LocalTime.now());

        // when
        var expected = time.withId(10L);

        // then
        assertThat(expected.id()).isEqualTo(10L);
    }
}
