package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void add() {
        //given
        Reservations reservations = new Reservations();

        Reservation newReservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40));

        //when //then
        assertThatCode(() -> reservations.add(newReservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("동일한 날짜와 시간을 예약하면 예외가 발생한다.")
    @Test
    void duplicateAdd() {
        //given
        Reservations reservations = new Reservations();

        Reservation newReservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40));

        Reservation newReservation1 = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40));

        //when //then
        reservations.add(newReservation);
        assertThatThrownBy(() -> reservations.add(newReservation1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 해당 시간에 예약한 이름이 존재합니다.");
    }

}
