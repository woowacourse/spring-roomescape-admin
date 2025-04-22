package roomescape.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
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
import roomescape.dto.ReservationGetResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationRestController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationGetResponse>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationGetResponse> reservationGetResponses = reservations.stream()
                .map(ReservationGetResponse::from)
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationGetResponses);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        try {
            Reservation reservationExcludeIndex = new Reservation(reservationCreateRequest.name(), reservationCreateRequest.date(), reservationCreateRequest.time());
            Reservation reservation = reservationRepository.insertAndGet(reservationExcludeIndex);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(reservation);
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
