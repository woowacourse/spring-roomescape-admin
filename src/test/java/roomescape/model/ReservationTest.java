package roomescape.model;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("예약 내역의 속성에 널 값을 입력한 경우 예외 처리한다")
    @Test
    void throwException_NullValue() {
        LocalDate date = LocalDate.parse("2025-04-17");
        LocalTime time = LocalTime.parse("19:00");

        assertSoftly(softAssertions -> {
            List<ThrowingCallable> callables = List.of(
                    () -> new Reservation(1, null, date, time),
                    () -> new Reservation(1, "kim", null, time),
                    () -> new Reservation(1, "kim", date, null)
            );

            callables.forEach(callable ->
                    softAssertions.assertThatThrownBy(callable)
                            .isInstanceOf(NullPointerException.class)
            );
        });
    }
}
