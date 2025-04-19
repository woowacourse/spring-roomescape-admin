package roomescape.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

class ReservationTest {

    @ParameterizedTest
    @MethodSource("invalidNames")
    void 공백이거나_이름이_비었으면_에러가_발생한다(String reservationName) {
        Long id = 1L;
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        Assertions.assertThatThrownBy(() -> new Reservation(id, reservationName, localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidNames() {
        return Stream.of(
                Arguments.of(" "),
                Arguments.of("")
        );
    }

    @Test
    void 예약_생성_시_null_값이_들어올_수_없다() {
        Assertions.assertThatThrownBy(() -> new Reservation(null, null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
