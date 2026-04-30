package roomescape.reservation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        reservation = new Reservation(1L, "한다", LocalDate.of(2023, 8, 5),
                new ReservationTime(1L, LocalTime.of(15, 40)));
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

}