package roomescape.reservationtime.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.service.ReservationTimeService;

@RestController
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @GetMapping("/times")
    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeService.findAllReservationTimes();
    }

    @PostMapping("/times")
    public ReservationTime saveReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.saveReservationTime(reservationTimeRequest);
    }

    @DeleteMapping("/times/{id}")
    public int deleteById(@PathVariable Long id) {
        return reservationTimeService.deleteById(id);
    }
}
