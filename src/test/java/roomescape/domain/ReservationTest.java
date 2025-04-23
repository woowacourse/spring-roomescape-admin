package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.exception.PastReservationException;

class ReservationTest {

    private final ReserverName name = new ReserverName("엠제이");
    private final ReservationDate date = new ReservationDate(LocalDate.now().plusDays(10));
    private final ReservationTime time = new ReservationTime(LocalTime.of(9, 0));

    @DisplayName("이름, 날짜, 시간으로 생성한다.")
    @Test
    void createReservation() {
        // given

        // when & then
        assertThatCode(() -> new Reservation(name, date, time))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 날짜면 예외가 발생한다.")
    @Test
    void createReservationWithoutName() {
        // given
        final ReservationDate pastDate = new ReservationDate(LocalDate.now().minusDays(10));

        // when & then
        assertThatThrownBy(() -> new Reservation(name, pastDate, time))
                .isInstanceOf(PastReservationException.class);
    }


}
