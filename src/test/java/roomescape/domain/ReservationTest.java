package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.BadRequestException;
import roomescape.fixture.ReservationTimeFixture;
import roomescape.fixture.ThemeFixture;

class ReservationTest {

    @Test
    @DisplayName("공백이나 null로만 이루어진 이름으로 예약할 수 없다.")
    void reservationFailByName() {
        // given
        String name1 = null;
        String name2 = "";
        ReservationTime time = ReservationTimeFixture.time2();
        Theme theme = ThemeFixture.theme2();

        // when & then
        assertThatThrownBy(() -> new Reservation(name1, LocalDate.now().plusDays(1), time, theme))
                .isInstanceOf(BadRequestException.class);
        assertThatThrownBy(() -> new Reservation(name2, LocalDate.now().plusDays(1), time, theme))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("예약자의 이름은 20자를 초과할 수 없다.")
    void reservationFailByNameTooLong() {
        // given
        String name1 = Strings.repeat("A", 20);
        String name2 = Strings.repeat("A", 21);
        ReservationTime time = ReservationTimeFixture.time2();
        Theme theme = ThemeFixture.theme2();

        // when & then
        assertThatCode(() -> new Reservation(name1, LocalDate.now().plusDays(1), time, theme))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> new Reservation(name2, LocalDate.now().plusDays(1), time, theme))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이전이면 true를 반환한다.")
    void isBeforeTrue() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(early);
        Reservation reservation = new Reservation("카고", date, time, ThemeFixture.theme1());

        // when 
        boolean result = reservation.isBefore(LocalDateTime.of(date, late));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이후이면 false를 반환한다.")
    void isBeforeFalse() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(late);
        Reservation reservation = new Reservation("카고", date, time, ThemeFixture.theme1());

        // when
        boolean result = reservation.isBefore(LocalDateTime.of(date, early));

        // then
        assertThat(result).isFalse();
    }
}
