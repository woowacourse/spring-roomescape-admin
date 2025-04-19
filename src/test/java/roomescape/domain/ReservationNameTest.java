package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationNameTest {

    @Test
    void 예약명을_올바르게_생성한다() {
        // given
        String name = "방탈출 예약";

        // when
        ReservationName reservationName = new ReservationName(name);

        // then
        assertThat(reservationName.getValue()).isEqualTo(name);
    }

    @Test
    void 예약명은_null일_수_없다() {
        // when & then
        assertThatThrownBy(() -> new ReservationName(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약명은 null일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    void 예약명은_공백일_수_없다(String blankString) {
        // when & then
        assertThatThrownBy(() -> new ReservationName(blankString))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약명은 공백일 수 없습니다.");
    }

    @Test
    void 예약명이_10자보다_길면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new ReservationName("12345678901"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약명은 10자 이내여야 합니다.");
    }
}
