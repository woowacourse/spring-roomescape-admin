package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationServiceResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationServiceResponse> reservationServiceResponses = reservationService.findAll();

        List<ReservationResponse> reservationResponses = reservationServiceResponses.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        ReservationServiceRequest reservationServiceRequest = reservationRequest.toReservationServiceRequest();
        ReservationServiceResponse reservationServiceResponse = reservationService.create(reservationServiceRequest);

        return ResponseEntity.created(URI.create("/reservations/" + reservationServiceResponse.id()))
                .body(ReservationResponse.from(reservationServiceResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reservationService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
