package roomescape.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationRepository reservationRepository = new MemoryReservationRepository();

    @GetMapping("")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservations);
    }

    @PostMapping("")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        try {
            Reservation reservation = new Reservation(reservationCreateRequest.name(), reservationCreateRequest.date(), reservationCreateRequest.time());
            reservationRepository.add(reservation);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(reservationRepository.findLast());
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
