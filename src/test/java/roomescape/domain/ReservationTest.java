package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.exception.CustomException;
import roomescape.fixture.ReservationFixture;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTest {

    @Nested
    @DisplayName("예약 시점이 현재보다 과거이면 예외가 발생해야 한다.")
    class isPastTense {

        @DisplayName("예약 시점이 과거이면 예외가 발생한다.")
        @Test
        void isPastTense_throwsExceptionByPastTime() {
            // given
            String dummyName = "kali";

            // when & then
            Assertions.assertThatCode(
                    () -> ReservationFixture.createPastReservationBeforeOneDay(dummyName)
            ).isInstanceOf(CustomException.class);
        }

        @DisplayName("예약 시점이 미래이면 예외를 발생하지 않는다.")
        @Test
        void isPastTense_doesNotThrowExceptionByFutureTime() {
            // given
            String dummyName = "kali";

            // when & then
            Assertions.assertThatCode(
                    () -> ReservationFixture.createFutureReservationAfterOneDay(dummyName)
            ).doesNotThrowAnyException();
        }
    }
}
