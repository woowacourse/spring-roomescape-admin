package roomescape.reservationtime.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservationtime.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    private ReservationTimeApiController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    ResponseEntity<List<ReservationTimeResponse>> getAll() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.getReservationTimes();
        return ResponseEntity.ok().body(reservationTimeResponses);
    }

    @PostMapping("/times")
    ResponseEntity<ReservationTimeResponse> create(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponse newReservationTimeResponse = reservationTimeService.saveReservationTime(reservationTimeRequest);
        return ResponseEntity.ok().body(newReservationTimeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}