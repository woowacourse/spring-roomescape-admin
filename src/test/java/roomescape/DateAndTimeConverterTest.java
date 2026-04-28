package roomescape;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateAndTimeConverterTest {
    @Test
    void 문자열을_LocalDate_객체로_파싱한다() {
        assertThat(DateAndTimeConverter.parseToDate("2026-04-28"))
                .isEqualTo(LocalDate.of(2026, 4, 28));
    }

    @Test
    void 날짜_입력_형식이_올바르지_않으면_예외를_던진다() {
        assertThatThrownBy(() -> DateAndTimeConverter.parseToDate("2026/04/28"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열을_LocalTime_객체로_파싱한다() {
        assertThat(DateAndTimeConverter.parseToTime("16:00"))
                .isEqualTo(LocalTime.of(16, 0));
    }

    @Test
    void 시간_입력_형식이_올바르지_않으면_예외를_던진다() {
        assertThatThrownBy(() -> DateAndTimeConverter.parseToTime("16시 00분"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void LocalDate_객체를_yyyy_mm_dd_형식의_문자열로_변환한다() {
        LocalDate date = LocalDate.of(2026, 4, 28);
        assertThat(DateAndTimeConverter.formatDate(date))
                .isEqualTo("2026-04-28");
    }

    @Test
    void LocalTime_객체를_hh_mm_형식의_문자열로_변환한다() {
        LocalTime time = LocalTime.of(16, 0);
        assertThat(DateAndTimeConverter.formatTime(time))
                .isEqualTo("16:00");
    }

}
