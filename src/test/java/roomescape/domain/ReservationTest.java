package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("같은 아이디를 가진 예약 객체인지 확인합니다.")
    void sameIdTest() {
        Person person = new Person("이름");
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Reservation reservation = new Reservation(1, person, LocalDate.of(2024, 4, 25), reservationTime);

        assertAll(
                () -> assertThat(reservation.isSameId(1)).isTrue(),
                () -> assertThat(reservation.isSameId(2)).isFalse()
        );
    }
}
