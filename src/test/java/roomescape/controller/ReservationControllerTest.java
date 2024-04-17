package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

class ReservationControllerTest {
    @Test
    @DisplayName("예약 정보를 잘 저장하는지 확인한다.")
    void saveReservation() {
        ReservationController reservationController = new ReservationController();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        ReservationResponse saveResponse = reservationController.saveReservation(
                new ReservationRequest(date, "폴라", time));

        long id = Objects.requireNonNull(saveResponse).id();
        ReservationResponse expected = new ReservationResponse(id, "폴라", date, time);

        Assertions.assertThat(saveResponse)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("예약 정보를 잘 불러오는지 확인한다.")
    void findAllReservations() {
        ReservationController reservationController = new ReservationController();
        List<ReservationResponse> allReservations = reservationController.findAllReservations();

        Assertions.assertThat(allReservations)
                .isEmpty();
    }

    @Test
    @DisplayName("예약 정보를 잘 지우는지 확인한다.")
    void delete() {
        List<Reservation> reservations = List.of(new Reservation(1, "폴라", LocalDateTime.now()));
        ReservationController reservationController = new ReservationController(new ArrayList<>(reservations));
        reservationController.delete(1L);

        List<ReservationResponse> reservationResponses = reservationController.findAllReservations();

        Assertions.assertThat(reservationResponses)
                .isEmpty();
    }
}
