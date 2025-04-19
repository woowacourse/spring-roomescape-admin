package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ReservationTest {

    public static Stream<Arguments> NullValues() {
        return Stream.of(
                Arguments.of(
                        null,
                        "brown",
                        LocalDate.of(2023, 12, 1),
                        LocalTime.of(1, 1)
                ),
                Arguments.of(
                        1L,
                        null,
                        LocalDate.of(2023, 12, 1),
                        LocalTime.of(1, 1)
                ),
                Arguments.of(
                        1L,
                        "brown",
                        null,
                        LocalTime.of(1, 1)
                ),
                Arguments.of(
                        1L,
                        "brown",
                        LocalDate.of(2023, 12, 1),
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("NullValues")
    @DisplayName("예약 생성 시 모든 값들이 존재하지 않으면 예외가 발생한다")
    void ExceptionAnyValueNull(Long id, String name, LocalDate date, LocalTime time) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Reservation(id, name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 여섯 글자 이상이면 예외가 발생한다")
    void ExceptionNameLength() {
        // given
        // when

        // then
        assertThatThrownBy(() -> new Reservation(
                1L,
                "여섯글자이름",
                LocalDate.of(2023, 12, 1),
                LocalTime.of(1, 1))
        )
                .isInstanceOf(IllegalArgumentException.class);
    }
}
