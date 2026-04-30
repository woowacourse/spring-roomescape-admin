package roomescape.time.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    private ReservationTime reservationTime;

    @BeforeEach
    void setUp() {
        reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("예약 시간 id를 가져온다.")
    void getId() {
        //given
        Long expected = 1L;

        //when
        Long actual = reservationTime.id();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("예약 시작 시간을 가져온다.")
    void getStartAt() {
        //given
        LocalTime expected = LocalTime.of(10, 0);

        //when
        LocalTime actual = reservationTime.startAt();

        //then
        assertEquals(expected, actual);
    }
}