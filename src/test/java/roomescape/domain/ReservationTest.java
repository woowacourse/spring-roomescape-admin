package roomescape.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 예약_추가_시_예약이_생성된다() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 8, 5);
        LocalTime time = LocalTime.of(15, 40);

        List<Reservation> reservations = new ArrayList<>();

        // when
        Reservation reservation = Reservation.of(id, name, date, time);
        reservations.add(reservation);

        // then
        Assertions.assertThat(reservations.size()).isEqualTo(1);
    }
}