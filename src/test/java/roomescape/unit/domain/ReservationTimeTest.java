package roomescape.unit.domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.validation.ReservationTimeValidator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeTest {

    @Test
    void 이전_시간에_추가할_수_없다() {
        LocalTime beforeTime = LocalTime.now().minusHours(1L);
        ReservationTime reservationTime = new ReservationTime(1L, beforeTime);
        Assertions.assertThat(new ReservationTimeValidator().isValid(reservationTime, null))
                .isFalse();
    }

    @Test
    void 이후_시간에_추가할_수_있다() {
        LocalTime afterTime = LocalTime.now().plusHours(1L);

        ReservationTime reservationTime = new ReservationTime(1L, afterTime);
        Assertions.assertThat(new ReservationTimeValidator().isValid(reservationTime, null))
                .isTrue();
    }
}

