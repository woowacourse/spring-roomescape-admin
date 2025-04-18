package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

class ReservationsTest {

    private Reservations reservations;

    @BeforeEach
    void setUp() {
        reservations = new Reservations(new ArrayList<>());
    }

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given

        // when

        // then
        assertThat(reservations.findAll().size()).isEqualTo(0);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservations.add("체체", LocalDate.now(), LocalTime.now().plusHours(1));

        // then
        assertThat(reservations.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given

        // when
        reservations.add("체체", LocalDate.now(), LocalTime.now().plusHours(1));
        List<Reservation> reservations = this.reservations.findAll();
        Reservation findReservation = reservations.getFirst();
        this.reservations.remove(findReservation.getId());

        // then
        assertThat(this.reservations.findAll().size()).isEqualTo(0);
    }

}
