package roomescape.domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTimeTest {

    @Test
    void 시작시간이_null일_경우_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "9, 59, 59, true",
            "10, 0, 1, false"
    })
    void 특정시간보다_이후인지_알_수_있다(int hour, int minute, int sec, boolean expected) {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));
        LocalTime comparedTime = LocalTime.of(hour, minute, sec);
        // when
        boolean isAfter = time.isAfter(comparedTime);
        // then
        Assertions.assertThat(isAfter).isEqualTo(expected);
    }

}