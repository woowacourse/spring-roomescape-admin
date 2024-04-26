package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ReservationTest {
    @Test
    @DisplayName("이름,에약날짜,예약시간 도메인을 통해 예약을 생성한다.")
    void create_domain_with_name_reservationDate_reservationTime_domain() {
        final Name name = new Name("조이썬");
        final ReservationDate date = ReservationDate.from("2023-10-03");
        final ReservationTime time = ReservationTime.from("10:00");
        assertThatCode(() -> new Reservation(1L, name, date, time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이름,예약날짜 문자열과 예약시간 도메인을 통해 예약을 생성한다. ")
    void create_domain_with_name_reservationDate_string_and_reservationTime_domain() {
        final ReservationTime time = ReservationTime.from("10:00");
        assertThatCode(() -> Reservation.from(1L, "조이썬", "2023-10-03", time))
                .doesNotThrowAnyException();
    }
}
