package roomescape.reservation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationResponse> getAllReservations() {
        return reservationService.findAllReservations();
    }

    @PostMapping("/reservations")
    public ReservationResponse addReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.saveReservation(reservationRequest);
    }

    @DeleteMapping("/reservations/{id}")
    public int deleteReservation(@PathVariable Long id) {
        return reservationService.deleteById(id);
    }
}
