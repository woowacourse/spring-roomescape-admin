package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.request.CreateReservationRequest;

@RestController
@RequestMapping(value = "/reservations")
public class RoomescapeController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public RoomescapeController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.reservationTimeId())
                .orElseThrow(IllegalArgumentException::new);
        Reservation reservation = Reservation.createWithoutId(request.name(), request.date(), reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        Reservation foundReservation = reservationDao.findById(reservationId)
                .orElseThrow(IllegalArgumentException::new);
        reservationDao.delete(foundReservation);
        return ResponseEntity.ok()
                .build();
    }
}
