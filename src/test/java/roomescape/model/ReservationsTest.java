package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    private final Reservations reservations = new Reservations();

    @DisplayName("예약 내역을 조회하는 기능을 구현한다")
    @Test
    void readReservations() {
        assertThatCode(reservations::findReservations).doesNotThrowAnyException();
    }

    @DisplayName("예약 내역을 추가하는 기능을 구현한다")
    @Test
    void createReservation() {
        Reservation reservation = new Reservation(1, "kim", LocalDate.parse("2025-04-19"), LocalTime.parse("21:00"));

        assertThatCode(() -> reservations.saveReservation(reservation)).doesNotThrowAnyException();
    }

    @DisplayName("예약 내역을 삭제하는 기능을 구현한다")
    @Test
    void deleteReservation() {
        Reservation reservation = new Reservation(1, "kim", LocalDate.parse("2025-04-19"), LocalTime.parse("21:00"));

        reservations.saveReservation(reservation);

        assertThatCode(() -> reservations.deleteReservationById(1L)).doesNotThrowAnyException();
    }

    @DisplayName("동일 날짜의 동일 시간대에 예약하려는 경우 예외 처리한다")
    @Test
    void throwException_Duplicates() {
        LocalDate date = LocalDate.parse("2025-04-18");
        LocalTime time = LocalTime.parse("20:00");

        reservations.saveReservation(new Reservation(1, "brie", date, time));

        assertThatThrownBy(() -> reservations.saveReservation(new Reservation(2, "neo", date, time)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("삭제하려는 예약 데이터의 아이디가 존재하지 않는 경우 예외 처리한다")
    @Test
    void throwException_InvalidId() {
        assertThatThrownBy(() -> reservations.deleteReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
