package roomescape.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RequestMapping("/times")
@RestController
public class TimesController {

    ReservationTimeService reservationTimeService;

    public TimesController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();

        return ResponseEntity.ok()
                .body(reservationTimes);
    }

    @PostMapping
    ResponseEntity<ReservationTimeResponse> addTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.add(reservationTimeRequest);

        return ResponseEntity.ok()
                .body(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        reservationTimeService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
