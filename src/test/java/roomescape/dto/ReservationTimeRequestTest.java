package roomescape.dto;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeRequestTest {

    @Test
    void 예약시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new ReservationTimeRequest(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
