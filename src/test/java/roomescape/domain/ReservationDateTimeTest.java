package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.TestBase;

public class ReservationDateTimeTest extends TestBase {

    @Autowired
    private Clock clock;

    @Test
    void 예약_일시는_현재일시_이전이면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> new ReservationDateTime(
                LocalDate.of(2025, 1, 1),
                LocalTime.of(10, 10, 10),
                clock
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_일시는_현재일시_이후여야_한다() {
        // given & when & then
        assertThatCode(() -> new ReservationDateTime(
                LocalDate.of(2025, 1, 2),
                LocalTime.of(10, 10, 10),
                clock
        )).doesNotThrowAnyException();
    }
}
