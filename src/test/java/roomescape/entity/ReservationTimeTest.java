package roomescape.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeTest {

    @Test
    void 예약시간은_null일_수_없다() {
        assertThatThrownBy(() -> ReservationTime.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}