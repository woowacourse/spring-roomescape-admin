package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTimeTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "9:00",
            "25:00",
            "12:64",
            "12-30",
            "12:3",
    })
    @DisplayName("시간이 정상 형태가 아닌 경우 예외를 발생한다.")
    void throwException_When_TimeIllegalFormat(String input) {
        assertThatThrownBy(() -> new ReservationTime(1L, input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간이 null인 경우 예외를 발생한다.")
    void throwException_When_TimeIsNull() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정상적인 시간인 경우 예외가 발생하지 않는다.")
    void makeTime_When_legalTime() {
        assertThatCode(() -> new ReservationTime(1L,"12:30"))
                .doesNotThrowAnyException();
    }
//
//    @Test
//    @DisplayName("예약 시간은 아이디와 시작시간을 가진다.")
//    void reservationTimeHave_IdAndStartedAt() {
//        ReservationTime time = new ReservationTime(1L, "10:00");
//
//        assertThat(time.getId()).isEqualTo(1L);
//        assertThat(time.getStartAt()).isEqualTo("10:00");
//    }
//
//    @Test
//    @DisplayName("id가 0 이하이면 예외를 발생한다.")
//    void throwException_when_IdUnderZero() {
//        assertThatThrownBy(() -> new ReservationTime(-1L, "10:00"))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
}
