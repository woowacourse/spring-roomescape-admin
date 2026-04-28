package roomescape;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationTest {

    @Test
    @DisplayName("Reservation은 id, name, date, time을 파라미터로 받아 생성된다.")
    void createReservation_success() {
        // given
        Long id = 1L;
        String name = "name";
        LocalDate reservationDate = LocalDate.now();
        LocalTime reservationTime = LocalTime.now();

        // when
        Reservation reservation = new Reservation(id, name, reservationDate, reservationTime);

        // then
        Assertions.assertThat(reservation).isNotNull();
    }
}
