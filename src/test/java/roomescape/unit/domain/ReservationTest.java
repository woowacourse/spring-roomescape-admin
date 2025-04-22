package roomescape.unit.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationValidator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 이전_날짜에_예약할_수_없다() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        LocalTime localTime = LocalTime.now();
        Reservation reservation = new Reservation(1L, "투다", localDate, localTime);
        Assertions.assertThat(new ReservationValidator().isValid(reservation, null))
                .isFalse();
    }

    @Test
    void 같은날짜일시_이전_시간에_예약할_수_없다() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now().minusHours(1);
        Reservation reservation = new Reservation(1L, "투다", localDate, localTime);
        Assertions.assertThat(new ReservationValidator().isValid(reservation, null))
                .isFalse();
    }

    @Test
    void 이후_날짜에_예약할_수_있다() {
        LocalDate localDate = LocalDate.now().plusDays(1);
        LocalTime localTime = LocalTime.now();
        Reservation reservation = new Reservation(1L, "투다", localDate, localTime);
        Assertions.assertThat(new ReservationValidator().isValid(reservation, null))
                .isTrue();
    }

    @Test
    void 같은날짜일시_이후_시간_예약할_수_있다() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now().plusHours(1);
        Reservation reservation = new Reservation(1L, "투다", localDate, localTime);
        Assertions.assertThat(new ReservationValidator().isValid(reservation, null))
                .isTrue();
    }
}

