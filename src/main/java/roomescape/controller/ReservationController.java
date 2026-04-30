package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ReservationCreateResponse create(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.create(request);
        return ReservationCreateResponse.from(reservation);
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
