package roomescape.controller;

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
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> findAllReservations() {
        return service.findAllReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        Reservation saved = service.add(request);
        return ReservationResponse.from(saved);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        service.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
