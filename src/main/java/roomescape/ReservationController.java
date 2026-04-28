package roomescape;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationsDao reservationsDao;

    @Autowired
    public ReservationController(InMemoryReservationsDao reservationsDao) {
        this.reservationsDao = reservationsDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationInfo>> getReservationsDao() {
        List<Reservation> reservations = reservationsDao.getReservations();

        List<ReservationInfo> reservationsInfo = reservations.stream()
                .map(ReservationInfo::from)
                .toList();

        return ResponseEntity.ok(reservationsInfo);
    }

    @PostMapping
    public ResponseEntity<ReservationInfo> createReservation(
            @RequestBody ReservationRequest request
    ) {
        Reservation reservation = request.to();
        Long id = reservationsDao.addReservation(reservation);

        ReservationInfo reservationInfo = new ReservationInfo(
                id,
                request.name(),
                request.date(),
                request.time()
        );

        return ResponseEntity.ok(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationsDao.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
