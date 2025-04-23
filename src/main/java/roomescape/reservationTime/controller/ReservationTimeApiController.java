package roomescape.reservationTime.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.common.Dao;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.dto.ReservationTimeDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final Dao<ReservationTime> timeDao;

    public ReservationTimeApiController(Dao<ReservationTime> timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> add(@RequestBody ReservationTimeDto reservationTimeDto) {
        ReservationTime time = reservationTimeDto.createTime();
        return ResponseEntity.ok(timeDao.add(time));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getAll() {
        return ResponseEntity.ok(timeDao.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
