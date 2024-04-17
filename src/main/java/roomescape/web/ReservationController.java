package roomescape.web;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationMemoryDao;
import roomescape.web.dto.ReservationFindAllResponse;
import roomescape.web.dto.ReservationFindResponse;
import roomescape.web.dto.ReservationSaveRequest;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationDao reservationDao = new ReservationMemoryDao();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<ReservationFindAllResponse>> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationFindAllResponse> response = reservations.stream()
                .map(ReservationFindAllResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationFindResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
        Reservation reservation = request.toEntity(counter.incrementAndGet());
        reservationDao.save(reservation);
        ReservationFindResponse response = ReservationFindResponse.from(reservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "reservation_id") Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
        return ResponseEntity.ok().build();
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow();
    }
}
