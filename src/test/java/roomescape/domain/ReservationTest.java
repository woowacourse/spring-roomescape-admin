package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTest {

    @Nested
    @DisplayName("예약 시점이 현재보다 과거이면 예외가 발생해야 한다.")
    class isPastTense {

        @DisplayName("예약 시점이 과거이면 예외가 발생한다.")
        @Test
        void isPastTense_throwsExceptionByPastTime() {
            // given
            LocalDateTime pastDateTime = LocalDateTime.now().minusHours(1);

            String dummyName = "kali";
            LocalDate inputDate = pastDateTime.toLocalDate();
            LocalTime inputTime = pastDateTime.toLocalTime();

            // when & then
            Assertions.assertThatCode(
                    () -> Reservation.of(dummyName, inputDate, inputTime)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("예약 시점이 미래이면 예외를 발생하지 않는다.")
        @Test
        void isPastTense_doesNotThrowExceptionByFutureTime() {
            // given
            LocalDateTime futureDateTime = LocalDateTime.now().plusHours(1);

            String dummyName = "kali";
            LocalDate inputDate = futureDateTime.toLocalDate();
            LocalTime inputTime = futureDateTime.toLocalTime();

            // when & then
            Assertions.assertThatCode(
                    () -> Reservation.of(dummyName, inputDate, inputTime)
            ).doesNotThrowAnyException();
        }
    }
}
