package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    public TimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> reservationTimes = reservationTimeDao.readAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        int createdId = reservationTimeDao.create(request.toReservationTime());
        reservationTime.setId(createdId);
        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable int id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
