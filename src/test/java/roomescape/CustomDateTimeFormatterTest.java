package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.util.CustomDateTimeFormatter;

class CustomDateTimeFormatterTest {

    @DisplayName("날짜 문자열 값을 받아 LocalDate 객체를 반환한다.")
    @Test
    void getLocalDateTest() {
        LocalDate localDate = CustomDateTimeFormatter.getLocalDate("2023-08-05");
        assertThat(localDate).isEqualTo(LocalDate.of(2023, 8, 5));
    }

    @DisplayName("시간 문자열 값을 받아 LocalTime 객체를 반환한다.")
    @Test
    void getLocalTimeTest() {
        LocalTime localTime = CustomDateTimeFormatter.getLocalTime("15:40");
        assertThat(localTime).isEqualTo(LocalTime.of(15, 40));
    }

    @DisplayName("LocalDate 객체로부터 날짜 문자열 값을 반환한다.")
    @Test
    void getFormattedDateTest() {
        String formattedDate = CustomDateTimeFormatter.getFormattedDate(LocalDate.of(2023, 8, 5));
        assertThat(formattedDate).isEqualTo("2023-08-05");
    }

    @DisplayName("LocalTime 객체로부터 시간 문자열 값을 반환한다.")
    @Test
    void getFormattedTimeTest() {
        String formattedTime = CustomDateTimeFormatter.getFormattedTime(LocalTime.of(15, 40));
        assertThat(formattedTime).isEqualTo("15:40");
    }
}
