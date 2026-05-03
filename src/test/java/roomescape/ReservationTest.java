package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationTest {

    @Test
    void 이름이_비어있으면_예약_생성에_실패한다() {
        ReservationTime time = new ReservationTime(1L, "10:00");
        LocalDate date = LocalDate.of(2026, 9, 1);

        assertThatThrownBy(() -> new Reservation(null, "", date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
