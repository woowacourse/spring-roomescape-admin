package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static final ReservationTime TEST_TIME = ReservationTime.constructWithoutId("20:00");

    @Test
    @DisplayName("이름이 빈 값이면 오류가 발생한다")
    void name_blank_throw_exception() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                "",
                                "2026-04-29",
                                TEST_TIME
                        )
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 null이면 오류가 발생한다")
    void name_null_throw_exception() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                null,
                                "2026-04-29",
                                TEST_TIME
                        )
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("날짜가 빈 값이면 오류가 발생한다")
    void date_blank_throw_exception() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                "라티",
                                "",
                                TEST_TIME
                        )
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("날짜가 null이면 오류가 발생한다")
    void date_null_throw_exception() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                "라티",
                                null,
                                TEST_TIME
                        )
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("time이 null이면 NPE 가 발생한다")
    void time_null_throw_npe() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                "라티",
                                "2020-02-02",
                                null
                        )
                ).isInstanceOf(NullPointerException.class)
                .hasMessage("time은 도메인에서 필수값이며 null일 수 없습니다.");
    }

    @Test
    @DisplayName("날짜 형식이 yyyy-MM-dd 를 준수하지 않으면 오류가 발생한다")
    void dateFormat_fail() {
        Assertions.assertThatThrownBy(
                        () -> new Reservation(
                                null,
                                "라티",
                                "26-02-02",
                                TEST_TIME
                        )
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 날짜 형식입니다: 26-02-02");
    }
}
