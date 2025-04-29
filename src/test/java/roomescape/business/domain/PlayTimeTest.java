package roomescape.business.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayTimeTest {

    @DisplayName("객체 생성시 null을 들어올 수 없다.")
    @Test
    void validateNonNull() {
        // given
        final LocalTime invalidStartAt = null;
        final LocalTime validStartAt = LocalTime.MAX;
        final Long invalidId = null;

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> new PlayTime(invalidStartAt))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> PlayTime.createWithId(invalidId, validStartAt))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
