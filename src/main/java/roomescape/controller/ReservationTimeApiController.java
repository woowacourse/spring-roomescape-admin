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
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping()
    public List<ReservationTimeResponse> times() {
        return reservationTimeService.getAllReservationTimes();
    }

    @PostMapping
    public ReservationTimeResponse add(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.createReservationTime(reservationTimeRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
