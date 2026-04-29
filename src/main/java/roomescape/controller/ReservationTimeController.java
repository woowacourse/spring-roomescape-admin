package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService timeService;

    public ReservationTimeController(ReservationTimeDAO reservationTimeDAO) {
        this.timeService = new ReservationTimeService(reservationTimeDAO);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimeList = timeService.read();
        return ResponseEntity.ok().body(reservationTimeList);
    }
}
