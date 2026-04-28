package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationControllerTest {
    ReservationController controller;

    @BeforeEach
    void beforeEach() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, "브라운", "2023-08-05", "15:40"));

        controller = new ReservationController(reservations);
    }

    @Test
    @DisplayName("전체 예약에 대해서 조회한다.")
    void findAllReservationsTest(){
        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservationTest() {
        controller.addReservation(new Reservation(2L, "네오", "2023-08-06", "15:41"));

        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(2);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservationTest() {
        controller.deleteReservation(1l);
        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(0);
    }
}
