package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.reservation.domain.exception.PastReservationException;
import roomescape.reservation.domain.exception.ReserverNameEmptyException;

public class ReservationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void 예약자_이름은_비어있거나_null일_수_없다(String input) {
        assertThatThrownBy(() -> new ReserverName(input))
                .isInstanceOf(ReserverNameEmptyException.class);
    }

    @Test
    void 과거_시간으로_예약할_수_없다() {
        assertThatThrownBy(() -> new ReservationDateTime(
                LocalDateTime.of(
                        LocalDate.of(2025, 4, 20),
                        LocalTime.of(0, 0)
                )
        )).isInstanceOf(PastReservationException.class);
    }
}