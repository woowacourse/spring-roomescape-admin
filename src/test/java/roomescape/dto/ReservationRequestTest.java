package roomescape.dto;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationRequestTest {

    @Test
    void 예약일자는_null일_수_없다() {
        ReservationRequest reservationRequest = new ReservationRequest("듀이", null, LocalTime.now());
        assertThatThrownBy(() -> reservationRequest.toEntity())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약시간은_null일_수_없다() {
        ReservationRequest reservationRequest = new ReservationRequest("듀이", LocalDate.now(), null);
        assertThatThrownBy(() -> reservationRequest.toEntity())
                .isInstanceOf(IllegalArgumentException.class);
    }
}
