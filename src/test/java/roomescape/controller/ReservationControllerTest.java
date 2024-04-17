package roomescape.controller;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

        ResponseEntity<ReservationResponse> saveResponse = reservationController.saveReservation(
                new ReservationRequest(date, "폴라", time));

        assertAll(
                () -> Assertions.assertThat(saveResponse.getStatusCode())
                        .isEqualTo(HttpStatusCode.valueOf(200)),
                () -> {
                    ReservationResponse body = saveResponse.getBody();
                    long id = Objects.requireNonNull(body).id();
                    Assertions.assertThat(body)
                            .isEqualTo(new ReservationResponse(id, "폴라", date, time));
                }
        );
    }

    @Test
    @DisplayName("예약 정보를 잘 불러오는지 확인한다.")
    void findAllReservations() {
        ReservationController reservationController = new ReservationController();
        ResponseEntity<List<ReservationResponse>> allReservations = reservationController.findAllReservations();

        assertAll(
                () -> Assertions.assertThat(allReservations.getBody())
                        .isEmpty(),
                () -> Assertions.assertThat(allReservations.getStatusCode())
                        .isEqualTo(HttpStatusCode.valueOf(200))
        );
    }

    @Test
    @DisplayName("예약 정보를 잘 지우는지 확인한다.")
    void delete() {
        List<Reservation> reservations = List.of(new Reservation(1, "폴라", LocalDateTime.now()));
        ReservationController reservationController = new ReservationController(new ArrayList<>(reservations));
        ResponseEntity<Void> delete = reservationController.delete(1L);
        List<ReservationResponse> body = reservationController.findAllReservations().getBody();
        assertAll(
                () -> Assertions.assertThat(delete.getStatusCode())
                        .isEqualTo(HttpStatusCode.valueOf(200)),
                () -> Assertions.assertThat(body)
                        .isEmpty()
        );
    }
}
