package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Controller
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeDao.insert(reservationTimeRequest);
        return ResponseEntity.ok(ReservationTime.of(id, reservationTimeRequest));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readReservationTimes() {
        return ResponseEntity.ok(reservationTimeDao.findAllReservationTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
