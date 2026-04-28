package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    @Test
    @DisplayName("Reservation은 id, name, date, time을 파라미터로 받아 생성된다.")
    void createReservation_success() {
        // given
        Long id = 1L;
        String name = "name";
        LocalDate reservationDate = LocalDate.now();
        LocalTime reservationTime = LocalTime.now();

        // when
        Reservation reservation = new Reservation(id, name, reservationDate, reservationTime);

        // then
        assertThat(reservation).isNotNull();
    }

    @Test
    @DisplayName("같은 id를 가진 Reservation은 같은 객체이다.")
    void reservations_with_same_id_are_equal() {
        // given
        Reservation reservation1 = new Reservation(1L, "홍길동", LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));
        Reservation reservation2 = new Reservation(1L, "김철수", LocalDate.of(2024, 1, 2), LocalTime.of(11, 0));

        // when & then
        assertThat(reservation1).isEqualTo(reservation2);
    }
}
