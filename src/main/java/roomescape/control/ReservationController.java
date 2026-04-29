package roomescape.control;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.control.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        final List<ReservationResponse> reservationResponses = reservationRepository.findAll().stream()
                .map(ReservationResponse::of)
                .toList();
       return ResponseEntity.ok(reservationResponses);
   }
}
