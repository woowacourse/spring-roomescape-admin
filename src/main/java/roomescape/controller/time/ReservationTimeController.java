package roomescape.controller.time;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.time.ReservationTimeCreateRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.repository.time.ReservationTimeDao;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
        @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest
    ) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeCreateRequest.startAt());
        reservationTimeDao.save(reservationTime);

        return ResponseEntity.ok(ReservationTimeResponse.from(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTimeResponse> response = reservationTimeDao.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(
        @PathVariable long id
    ) {
        reservationTimeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
