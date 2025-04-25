package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("같은 아이디를 가진 예약 객체인지 확인합니다.")
    void sameIdTest() {
        Person person = new Person("이름");
        ReservationTime reservationTime = new ReservationTime(LocalDateTime.of(2024, 2, 25, 10, 0));
        Reservation reservation = new Reservation(1, person, reservationTime);

        assertAll(
                () -> assertThat(reservation.isSameId(1)).isTrue(),
                () -> assertThat(reservation.isSameId(2)).isFalse()
        );
    }
}
