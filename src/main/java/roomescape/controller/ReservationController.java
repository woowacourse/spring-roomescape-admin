package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAll();
    }

    @ResponseBody
    @PostMapping
    public ReservationResponse createReservation(@RequestBody final ReservationRequest reservationRequest) {
        return reservationService.create(reservationRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
