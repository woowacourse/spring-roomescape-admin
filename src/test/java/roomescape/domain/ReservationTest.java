package roomescape.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("문자열을 통해 예약을 생성한다.")
    void ReservationCreation() {
        // given
        Name expectedName = new Name("웨지");
        ReserveTime expectedTime = new ReserveTime(LocalDate.parse("2024-04-18"), LocalTime.parse("15:30"));
        // when
        Reservation actual = new Reservation("웨지", "2024-04-18", "15:30");
        // then
        assertAll(
                () -> assertEquals(expectedName, actual.getName()),
                () -> assertEquals(expectedTime, actual.getReserveTime())
        );
    }
}
