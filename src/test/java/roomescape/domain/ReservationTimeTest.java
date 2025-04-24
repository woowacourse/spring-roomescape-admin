package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    void 포멧_형식으로_변환해_반환한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(12, 0, 0));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // when
        String result = reservationTime.formatTime(timeFormatter);

        // then
        Assertions.assertThat(result).isEqualTo("12:00");
    }
}
