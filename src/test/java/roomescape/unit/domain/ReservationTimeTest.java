package roomescape.unit.domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeTest {

    @Test
    void 이전_시간에_추가할_수_없다() {
        LocalTime beforeTime = LocalTime.now().minusHours(1L);
        Assertions.assertThatThrownBy(() -> new ReservationTime(1L, beforeTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이후_시간에_추가할_수_있다() {
        LocalTime afterTime = LocalTime.now().plusHours(1L);
        Assertions.assertThatCode(() -> new ReservationTime(1L, afterTime))
                .doesNotThrowAnyException();
    }
}

