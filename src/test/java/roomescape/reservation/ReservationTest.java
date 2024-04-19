package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @DisplayName("동일한 id는 같은 예약이다.")
    @Test
    void equals() {
        //given
        long id = 1L;
        String name1 = "choco";
        LocalDate date1 = LocalDate.of(2024, 4, 19);
        LocalTime time1 = LocalTime.of(12, 23, 0);

        String name2 = "pororo";
        LocalDate date2 = LocalDate.of(2022, 4, 19);
        LocalTime time2 = LocalTime.of(11, 23, 0);

        //when
        Reservation reservation1 = new Reservation(id, name1, date1, time1);
        Reservation reservation2 = new Reservation(id, name2, date2, time2);

        //then
        assertThat(reservation1).isEqualTo(reservation2);
    }
}
