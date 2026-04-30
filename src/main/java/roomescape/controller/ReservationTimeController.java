package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        return reservationTimeService.createReservationTime(request);
    }

    @GetMapping("/times")
    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeService.getReservationTimes();
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
