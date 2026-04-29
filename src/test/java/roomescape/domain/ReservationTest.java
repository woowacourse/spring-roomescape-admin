package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 40));
    @Test
    @DisplayName("Reservation의 id가 비어있는 경우 id를 할당한다.")
    public void bindId_success() throws Exception {
        // given
        Reservation reservation = new Reservation(
                "name", LocalDate.of(2023, 8, 5), reservationTime);

        long id = 1;
        // when
        reservation.bindId(id);

        // then
        assertThat(reservation.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Reservation의 id가 할당되어 있는데 id를 할당하려는 경우 예외가 발생한다.")
    public void bindId_fail() throws Exception {
        // given
        Reservation reservation = new Reservation(
                1L, "name",
                LocalDate.of(2023, 8, 5), reservationTime);

        long id = 2;

        // when then
        assertThatThrownBy(() -> reservation.bindId(id));
    }

}
