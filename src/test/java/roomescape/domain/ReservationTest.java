package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.BadRequestException;

class ReservationTest {

    @Test
    @DisplayName("공백이나 null로만 이루어진 이름으로 예약할 수 없다.")
    void reservationFailByName() {
        // given
        String name1 = null;
        String name2 = "";
        ReservationTime time = new ReservationTime(LocalTime.now().plusMinutes(1));

        // when & then
        assertThatThrownBy(() -> new Reservation(name1, LocalDate.now(), time))
                .isInstanceOf(BadRequestException.class);
        assertThatThrownBy(() -> new Reservation(name2, LocalDate.now(), time))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("예약자의 이름은 20자를 초과할 수 없다.")
    void reservationFailByNameTooLong() {
        // given
        String name1 = Strings.repeat("A", 20);
        String name2 = Strings.repeat("A", 21);
        ReservationTime time = new ReservationTime(LocalTime.now().plusMinutes(1));

        // when & then
        assertThatCode(() -> new Reservation(name1, LocalDate.now(), time))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> new Reservation(name2, LocalDate.now(), time))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("현재 시각보다 이후의 예약은 가능하다.")
    void reservationSuccess() {
        // given
        ReservationTime time = new ReservationTime(LocalTime.now().plusMinutes(1));

        // when & then
        assertThatCode(() -> new Reservation("카고", LocalDate.now(), time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 시각보다 이전의 예약은 불가능하다.")
    void reservationFailByLate() {
        // given
        ReservationTime time = new ReservationTime(LocalTime.now().minusMinutes(1));

        // when & then
        assertThatThrownBy(() -> new Reservation("카고", LocalDate.now(), time))
                .isInstanceOf(BadRequestException.class);
    }
}
