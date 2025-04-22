package roomescape.time.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeDao;

@Controller
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        List<ReservationTimeResponse> responses = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .toList();
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        ReservationTime savedReservation = reservationTimeDao.save(request.toReservationTime());
        ReservationTimeResponse response = new ReservationTimeResponse(savedReservation);
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        boolean isRemoved = reservationTimeDao.removeById(id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
