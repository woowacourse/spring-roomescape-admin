package roomescape.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTime;
import roomescape.dao.ReservationTimeDao;
import roomescape.web.dto.ReservationFindResponse;
import roomescape.web.dto.ReservationSaveRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<ReservationFindResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationFindResponse::from)
                .toList();
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id);
    }

    @PostMapping
    public ResponseEntity<ReservationFindResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = request.toEntity(reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return ResponseEntity.ok()
                .header("Location", "/reservations/" + savedReservation.getId())
                .body(ReservationFindResponse.from(savedReservation));
    }

    @DeleteMapping("/{reservation_id}")
    public void deleteReservation(@PathVariable(value = "reservation_id") Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
    }
}
