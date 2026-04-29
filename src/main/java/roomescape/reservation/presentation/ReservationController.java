package roomescape.reservation.presentation;

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
import roomescape.reservation.dao.ReservationEntity;
import roomescape.reservation.dao.ReservationsDao;
import roomescape.reservation.presentation.dto.Reservation;
import roomescape.reservation.presentation.dto.ReservationRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationsDao reservationsDao;

    @Autowired
    public ReservationController(ReservationsDao reservationsDao) {
        this.reservationsDao = reservationsDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsDao() {
        List<ReservationEntity> reservationEntities = reservationsDao.getReservations();

        List<Reservation> reservationsInfo = reservationEntities.stream()
                .map(Reservation::from)
                .toList();

        return ResponseEntity.ok(reservationsInfo);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequest request
    ) {
        ReservationEntity reservationEntity = request.to();
        Long id = reservationsDao.saveReservation(reservationEntity);

        Reservation reservation = new Reservation(
                id,
                request.name(),
                request.date(),
                request.time()
        );

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationsDao.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
