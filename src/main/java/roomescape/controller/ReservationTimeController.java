package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@RestController
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    public List<TimeResponse> times() {
        return reservationTimeDao.getReservationTimes().stream()
                .map(TimeResponse::new)
                .toList();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest timeRequest) {
        ReservationTime reservationTime = reservationTimeDao.add(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + reservationTime.getId()))
                .body(new TimeResponse(reservationTime));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
