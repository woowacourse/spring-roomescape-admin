package roomescape.reservation.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.payload.ReservationRequest;
import roomescape.reservation.payload.ReservationResponse;
import roomescape.reservation.payload.ReservationWithTimeResponse;
import roomescape.reservation.repository.ReservationRepository;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> postReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationRepository.save(request);
        return ResponseEntity.ok().body(ReservationResponse.from(reservation));
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations() {
        List<ReservationWithTimeResponse> reservations = reservationRepository.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build(); // 요구사항에 맞춰서 noContent대신 ok를 return한다.
    }

}
