package roomescape;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationRequest request) {
        Reservation reservation = request.toEntity();
        Reservation saved = reservationRepository.save(reservation);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            Reservation found = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
            reservationRepository.delete(found);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
