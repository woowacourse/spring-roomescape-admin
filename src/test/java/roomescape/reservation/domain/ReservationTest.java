package roomescape.reservation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.time.domain.ReservationTime;

class ReservationTest {
    Reservation reservation;

    @BeforeEach
    void setup() {
        reservation = Reservation.of(1L, "한다", LocalDate.of(2023, 8, 5),
                ReservationTime.of(1L, LocalTime.of(15, 40)));
    }

    @Test
    @DisplayName("예약 id를 가져온다.")
    void getId() {
        //given
        Long expected = 1L;

        //when
        Long actual = reservation.id();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("예약자명을 가져온다.")
    void getName() {
        //given
        String expected = "한다";

        //when
        String actual = reservation.name();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("예약날짜를 가져온다.")
    void getDate() {
        //given
        LocalDate expected = LocalDate.of(2023, 8, 5);

        //when
        LocalDate actual = reservation.date();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("예약시간 id를 가져온다.")
    void getTime() {
        //given
        Long expected = 1L;

        //when
        Long actual = reservation.time().id();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("두 예약 객체의 동등성을 비교한다.")
    void equals() {
        //given & when
        Reservation otherReservation = Reservation.of(1L, "한다", LocalDate.of(2023, 8, 5),
                ReservationTime.of(1L, LocalTime.of(15, 40)));

        //then
        assertEquals(reservation, otherReservation);
    }

    @Test
    @DisplayName("아직 DB에 추가되지 않은 예약끼리와는 동등하지 않다.")
    void equals_null_id() {
        //given & when
        Reservation reservation1 = Reservation.create("한다", LocalDate.now(),
                ReservationTime.of(1L, LocalTime.of(10, 0)));
        Reservation reservation2 = Reservation.create("한다", LocalDate.now(),
                ReservationTime.of(1L, LocalTime.of(10, 0)));

        //then
        assertNotEquals(reservation1, reservation2);
    }
}