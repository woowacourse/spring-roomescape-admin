package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations(){
        List<ReservationResponse> reservations = reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::toDto)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.add(reservationRequest);
        return ResponseEntity.ok().body(ReservationResponse.toDto(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (InvalidReservationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
