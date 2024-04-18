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
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationService;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationService reservationService;

    public ReservationTimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> read() {
        List<ReservationTimeResponse> responses = reservationService.findAllReservationTime();

        return ResponseEntity.ok()
                .body(responses);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationService.createReservationTime(reservationTimeRequest);

        return ResponseEntity.created(URI.create("/times/" + id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationService.deleteReservationTimeById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
