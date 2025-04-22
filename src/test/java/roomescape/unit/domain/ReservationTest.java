package roomescape.unit.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 이전_날짜에_예약할_수_없다() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        LocalTime localTime = LocalTime.now();
        Assertions.assertThatThrownBy(() -> new Reservation(1L, "투다", localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 같은날짜일시_이전_시간에_예약할_수_없다() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now().minusHours(1);
        Assertions.assertThatThrownBy(() -> new Reservation(1L, "투다", localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이후_날짜에_예약할_수_있다() {
        LocalDate localDate = LocalDate.now().plusDays(1);
        LocalTime localTime = LocalTime.now();
        Assertions.assertThatCode(() -> new Reservation(1L, "투다", localDate, localTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 같은날짜일시_이후_시간_예약할_수_있다() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now().plusHours(1);
        Assertions.assertThatCode(() -> new Reservation(1L, "투다", localDate, localTime))
                .doesNotThrowAnyException();
    }
}

