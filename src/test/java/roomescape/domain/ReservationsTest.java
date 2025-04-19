package roomescape.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {

    @Test
    void 예약_기록을_추가할_수_있다() {
        // given
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(1L, "메이", LocalDateTime.now()));
        reservations.add(new Reservation(2L, "밍트", LocalDateTime.now()));

        // when & then
        assertThat(reservations.getReservations().size())
                .isEqualTo(2);
    }

    @Test
    void 존재하지_않는_예약_기록을_삭제하려는_경우_예외를_발생시킨다() {
        // given
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(1L, "메이", LocalDateTime.now()));
        reservations.add(new Reservation(2L, "밍트", LocalDateTime.now()));

        // when & then
        assertThatThrownBy(() -> reservations.deleteById(3L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다.");
    }
}
