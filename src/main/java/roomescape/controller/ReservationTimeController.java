package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> times(@RequestBody @Valid ReservationTimeRequest request) {
        return ResponseEntity.ok(reservationTimeService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> times(@PathVariable Long id) {
        reservationTimeService.remove(id);
        return ResponseEntity.ok().build();
    }
}
