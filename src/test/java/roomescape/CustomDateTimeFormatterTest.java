package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.util.CustomDateTimeFormatter;

class CustomDateTimeFormatterTest {

    @DisplayName("날짜와 시간 문자열 값을 받아 LocalDateTime 객체를 반환한다.")
    @Test
    void getLocalDateTimeTest() {
        LocalDateTime localDateTime = CustomDateTimeFormatter.getLocalDateTime("2023-08-05", "15:40");
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2023, 8, 5, 15, 40));
    }

    @DisplayName("LocalDateTime 객체로부터 날짜 문자열 값을 반환한다.")
    @Test
    void getFormattedDateTest() {
        String formattedDate = CustomDateTimeFormatter.getFormattedDate(LocalDateTime.of(2023, 8, 5, 15, 40));
        assertThat(formattedDate).isEqualTo("2023-08-05");
    }

    @DisplayName("LocalDateTime 객체로부터 시간 문자열 값을 반환한다.")
    @Test
    void getFormattedTimeTest() {
        String formattedDate = CustomDateTimeFormatter.getFormattedTime(LocalDateTime.of(2023, 8, 5, 15, 40));
        assertThat(formattedDate).isEqualTo("15:40");
    }
}
