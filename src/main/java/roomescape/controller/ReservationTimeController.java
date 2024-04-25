package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.request.ReservationTimeCreateRequest;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTime reservationTime = reservationTimeCreateRequest.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.fromReservationTime(savedReservationTime);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponse::fromReservationTime)
                .toList();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long reservationTimeId) {
        reservationTimeDao.delete(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
