package roomescape.business.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeTest {

    @DisplayName("객체 생성시 null을 들어올 수 없다.")
    @Test
    void validateNonNull() {
        // given & when & then
        assertAll(
                () -> assertThatThrownBy(() -> new Time(null))
                        .isInstanceOf(NullPointerException.class),
                () -> assertThatThrownBy(() -> Time.createWithId(null, LocalTime.MAX))
                        .isInstanceOf(NullPointerException.class)
        );
    }
}
