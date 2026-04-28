package roomescape;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {
    @Test
    void 예약_생성_시_예약자_명이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(null, "2026-04-28", "16:00")).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약자_명이_공백이라면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(" ", "2026-04-28", "16:00")).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_날짜가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", null, "16:00")).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_날짜_형식이_올바르지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", "2026/04/28", "16:00")).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", "2026-04-28", null)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_시간_형식이_올바르지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", "2026-04-28", "15시 00분")).isInstanceOf(
                IllegalArgumentException.class);
    }
}
