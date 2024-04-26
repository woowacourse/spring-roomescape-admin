package roomescape.web.controller;

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
import roomescape.web.controller.dto.ReservationTimeRequest;
import roomescape.web.controller.dto.ReservationTimeResponse;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.dto.ReservationTimeServiceRequest;
import roomescape.core.service.dto.ReservationTimeServiceResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeServiceResponse> reservationTimeServiceResponses = reservationTimeService.findAll();

        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeServiceResponses.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeServiceRequest reservationTimeServiceRequest = reservationTimeRequest.toReservationTimeServiceRequest();
        ReservationTimeServiceResponse reservationTimeServiceResponse = reservationTimeService.create(reservationTimeServiceRequest);

        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(reservationTimeServiceResponse);

        return ResponseEntity.created(URI.create("/times/" + reservationTimeResponse.id()))
                .body(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationTimeService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
