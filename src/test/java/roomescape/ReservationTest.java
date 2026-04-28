package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.spi.ResolveResult;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {
    LocalDate date;
    LocalTime time;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2026, 4, 28);
        time = LocalTime.of(16, 0);
    }

    @Test
    void 예약_생성_시_예약자_명이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(null, date, time)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약자_명이_공백이라면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(" ", date, time)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_날짜가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", null, time)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 예약_생성_시_예약_시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("브라운", date, null)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 날짜와_시간이_동일한_예약인지_반환한다() {
        Reservation neoReservation = new Reservation("네오", date, time);
        Reservation brownReservation = new Reservation("브라운", date, time);
        Reservation pobiReservation = new Reservation("포비", date, LocalTime.of(14, 0));

        assertThat(neoReservation.isSameDateTime(brownReservation)).isTrue();
        assertThat(neoReservation.isSameDateTime(pobiReservation)).isFalse();
    }
}
