package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservations(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = ReservationRequest.from(reservationRequest);
        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationResponse reservationResponse = ReservationResponse.from(savedReservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
