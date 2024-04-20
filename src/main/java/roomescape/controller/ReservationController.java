package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.repository.ReservationStore;

@RestController
public class ReservationController {
    private final ReservationStore reservationStore = new ReservationStore();

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = new Reservation(reservationRequest.name(), reservationRequest.date(), reservationRequest.time());

        long id = reservationStore.save(newReservation);

        return ResponseEntity.ok(new ReservationResponse(id, newReservation.getName(), newReservation.getDate(), newReservation.getTime()));
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readReservations() {

        return reservationStore.getAll().entrySet().stream()
                .map(entry -> {
                    Reservation reservation = entry.getValue();
                    return new ReservationResponse(
                            entry.getKey(),
                            reservation.getName(),
                            reservation.getDate(),
                            reservation.getTime()
                    );
                })
                .toList();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationStore.delete(id);

        return ResponseEntity.ok().build();
    }
}
