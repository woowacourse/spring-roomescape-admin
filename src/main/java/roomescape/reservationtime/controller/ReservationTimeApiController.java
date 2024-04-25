package roomescape.reservationtime.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    ResponseEntity<Object> create(@RequestBody @Valid final ReservationTimeRequest reservationTimeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("적절하지 않는 입력값 입니다.");
        }
        ReservationTimeResponse newReservationTimeResponse = reservationTimeService.saveReservationTime(reservationTimeRequest);
        return ResponseEntity.ok().body(newReservationTimeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}