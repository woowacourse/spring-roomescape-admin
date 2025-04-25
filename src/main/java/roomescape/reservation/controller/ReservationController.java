package roomescape.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "/admin/reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readAllReservations() {
        List<ReservationResponse> response = reservationService.getAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.save(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
