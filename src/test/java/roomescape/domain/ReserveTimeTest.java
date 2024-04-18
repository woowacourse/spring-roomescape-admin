package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReserveTimeTest {

    @Test
    @DisplayName("문자열을 통해 예약 시간을 생성한다.")
    void createByStringTest() {
        ReserveTime actual = new ReserveTime("2024-04-18", "15:30");
        assertAll(
                () -> assertThat(actual.getDate()).isEqualTo("2024-04-18"),
                () -> assertThat(actual.getTime()).isEqualTo("15:30")
        );
    }

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        ReserveTime reserveTime = new ReserveTime("2024-04-18", "15:30");
        assertThat(reserveTime).isEqualTo(new ReserveTime("2024-04-18", "15:30"));
    }
}