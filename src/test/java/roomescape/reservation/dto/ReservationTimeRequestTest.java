package roomescape.reservation.dto;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTimeRequestTest {

    @DisplayName("예약 시간은 HH:mm 형식이다.")
    @ParameterizedTest
    @ValueSource(strings = {"12:00", "23:59", "00:00"})
    void startAtFormatValidTest(String startAt) {
        // given
        LocalTime startAtInput = LocalTime.parse(startAt);

        // when
        assertThatCode(() -> new ReservationTimeRequest(startAtInput))
                .doesNotThrowAnyException();
    }

    @DisplayName("예약 시간은 HH:mm 형식이 아니면 예외가 발생한다.")
    @Test
    void startATFormatValidExceptionTest() {
        // given
        LocalTime startAtInput = LocalTime.parse("23:59:55");

        // when
        assertThatThrownBy(() -> new ReservationTimeRequest(startAtInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간 형식은 HH:mm 입니다.");
    }
}