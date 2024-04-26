package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponse reservationTimeSave(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.addReservationTime(reservationTimeRequest);
    }

    @GetMapping
    public List<ReservationTimeResponse> reservationTimesList() {
        return reservationTimeService.findReservationTimes();
    }

    @DeleteMapping("/{reservationTimeId}")
    public void reservationTimeRemove(@PathVariable long reservationTimeId) {
        reservationTimeService.removeReservationTime(reservationTimeId);
    }
}
