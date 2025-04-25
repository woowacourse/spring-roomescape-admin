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
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class AdminReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public AdminReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> getAllReservationTimes() {
        return ReservationTimeResponse.toResponses(reservationTimeService.findAll());
    }

    @PostMapping
    public ReservationTime getReservationTimeById(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.save(reservationTimeRequest.toReservationTime());
    }

    @DeleteMapping("/{id}")
    public void removeReservationTimeById(@PathVariable("id") Long id) {
        reservationTimeService.removeById(id);
    }
}
