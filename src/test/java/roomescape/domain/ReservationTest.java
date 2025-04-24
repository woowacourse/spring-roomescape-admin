package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("이름, 날짜, 시간으로 생성한다.")
    @Test
    void createReservation() {
        // given
        final ReserverName name = new ReserverName("엠제이");
        final ReservationDate date = new ReservationDate(LocalDate.now().plusDays(10));
        final ReservationTime time = new ReservationTime(LocalTime.of(9, 0));
        final ReservationDateTime dateTime = new ReservationDateTime(date, time);

        // when & then
        assertThatCode(() -> new Reservation(name, dateTime))
                .doesNotThrowAnyException();
    }
}
