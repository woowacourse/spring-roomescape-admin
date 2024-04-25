package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.dto.ReservationTimeFindResponse;
import roomescape.dto.ReservationTimeSaveRequest;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    public TimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<ReservationTimeFindResponse> findAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeFindResponse::from)
                .toList();
    }

    public ReservationTime findReservationTimeById(Long id) {
        return reservationTimeDao.findById(id);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeFindResponse> saveReservationTime(@RequestBody ReservationTimeSaveRequest request) {
        ReservationTime reservationTime = request.toEntity();
        ReservationTime saveReservationTime = reservationTimeDao.save(reservationTime);
        return ResponseEntity.ok()
                .header("Location", "/reservations/" + saveReservationTime.getId())
                .body(ReservationTimeFindResponse.from(saveReservationTime));
    }

    @DeleteMapping("/{reservation_time_id}")
    public void deleteReservationTime(@PathVariable(value = "reservation_time_id") Long id) {
        ReservationTime reservationtime = findReservationTimeById(id);
        reservationTimeDao.delete(reservationtime);
    }

}
