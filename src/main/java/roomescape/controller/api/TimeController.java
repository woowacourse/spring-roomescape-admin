package roomescape.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.reservationtime.ReservationTimeRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@RequestMapping("/times")
@Controller
public class TimeController {

    private ReservationTimeDao reservationTimeDao;

    public TimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAllReservationTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        long id = reservationTimeDao.saveReservationTime(reservationTimeRequest);
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(id,
                reservationTimeRequest.startAt().toString());

        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDao.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
