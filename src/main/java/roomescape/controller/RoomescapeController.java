package roomescape.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.domain.ReservationStore;

@RestController
public class RoomescapeController {
    private final ReservationStore reservationStore = new ReservationStore();

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation newReservation = new Reservation(reservationRequestDto.getName(), reservationRequestDto.getDate(), reservationRequestDto.getTime());

        reservationStore.save(newReservation);

        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> readReservations() {
        return reservationStore.getAll();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationStore.delete(id);

        return ResponseEntity.ok().build();
    }
}
