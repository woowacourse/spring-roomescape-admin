package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.createReservationTime(reservationTimeRequest);
    }

    @GetMapping
    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeService.getAllReservationTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
