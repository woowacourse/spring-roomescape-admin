package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @ResponseBody
    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.create(reservationTimeRequest);
    }

    @ResponseBody
    @GetMapping
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTimeById(@PathVariable final long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
