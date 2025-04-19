package roomescape.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationGroupTest {

    private ReservationGroup reservationGroup;

    @BeforeEach
    void init() {
        reservationGroup = new ReservationGroup();
    }

    @Test
    void 그룹애_존재하지_않는_예약을_삭제하는_경우() {
        Reservation person = new Reservation(1L, "person", LocalDate.now(), LocalTime.now());
        Long deleteId = 2L;

        reservationGroup.addReservation(person);

        Assertions.assertThatThrownBy(() -> reservationGroup.deleteReservationById(deleteId))
                .isInstanceOf(ResponseStatusException.class);
    }
}
