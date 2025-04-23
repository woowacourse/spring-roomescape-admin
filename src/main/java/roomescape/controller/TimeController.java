package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    public TimeController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();
        long id = reservationTimeDao.save(reservationTime);
        return ResponseEntity.ok(ReservationTimeResponse.of(id, reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> readTimes() {
        List<ReservationTime> times = reservationTimeDao.getAll();
        return ResponseEntity.ok().body(ReservationTimeResponse.from(times));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable final Long id) {
        int count = reservationTimeDao.delete(id);
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id에 대한 시간 정보가 존재하지 않습니다.");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        reservationTimeDao.deleteAll();
        return ResponseEntity.ok().build();
    }
}
