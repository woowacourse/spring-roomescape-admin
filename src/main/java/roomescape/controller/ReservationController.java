package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.create(reservationRequest));
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @ResponseBody
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> read(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @ResponseBody
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
