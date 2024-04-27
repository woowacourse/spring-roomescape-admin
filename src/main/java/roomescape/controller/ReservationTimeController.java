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
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeDao;

    public ReservationTimeController(ReservationTimeService reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.getReservationTimes();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeDao.createReservationTime(reservationTimeCreateRequest);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long reservationTimeId) {
        reservationTimeDao.deleteReservationTime(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
