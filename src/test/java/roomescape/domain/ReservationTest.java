package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        Long id = 1L;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String name = "아톰";

        assertThatCode(() -> new Reservation(id, name, date, time))
                .doesNotThrowAnyException();
    }
}
