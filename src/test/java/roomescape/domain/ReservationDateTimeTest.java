package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.exception.PastReservationException;

class ReservationDateTimeTest {


    @DisplayName("과거 날짜면 예외가 발생한다.")
    @Test
    void createPastReservationDateTime() {
        // given

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(new ReservationDate(LocalDate.now().minusDays(10)),
                new ReservationTime(LocalTime.of(9, 0))))
                .isInstanceOf(PastReservationException.class);
    }
}
