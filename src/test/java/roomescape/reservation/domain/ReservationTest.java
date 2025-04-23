package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.reservation.domain.exception.ReserverNameEmptyException;

public class ReservationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void 예약자_이름은_비어있거나_null일_수_없다(String input) {
        assertThatThrownBy(() -> new ReserverName(input))
                .isInstanceOf(ReserverNameEmptyException.class);
    }
}
