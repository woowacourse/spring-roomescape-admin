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
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationController {

    private final ReservationService service;
    private final ReservationTimeService timeService;

    public ReservationController(ReservationService service, ReservationTimeService timeService) {
        this.service = service;
        this.timeService = timeService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> readReservation() {
        return service.readReservation();
    }

    @PostMapping("/reservations")
    public ReservationResponse postReservation(@RequestBody ReservationRequest request) {
        timeService.existsTimeById(request.timeId());
        return service.postReservation(request);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
