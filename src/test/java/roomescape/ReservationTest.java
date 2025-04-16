package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void createTest() {
        // given
        int id = 1;
        String name = "브라운";
        LocalDate reservationDate = LocalDate.of(2023, 1, 1);
        LocalTime reservationTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, reservationTime))
                .doesNotThrowAnyException();
    }
}
