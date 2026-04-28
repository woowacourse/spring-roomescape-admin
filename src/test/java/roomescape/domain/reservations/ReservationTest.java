package roomescape.domain.reservations;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @Test
    @DisplayName("날짜를 String에서 LocalDate로 변환하는 기능")
    void date_String_to_LocalDate() {
        // given
        String date = "2023-08-05";

        // when
        LocalDate result = LocalDate.parse(date);

        // then
        assertThat(result).isEqualTo(LocalDate.of(2023, 8, 5));
    }

    @Test
    @DisplayName("시간을 String에서 LocalDateTime으로 변환하는 기능")
    void time_String_to_LocalDateTime() {
        // given
        String time = "15:40";

        // when
        LocalTime result = LocalTime.parse(time);

        // then
        assertThat(result).isEqualTo(LocalTime.of( 15, 40));
    }
}
