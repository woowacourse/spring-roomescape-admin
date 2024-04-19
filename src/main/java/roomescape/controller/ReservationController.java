package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        List<ReservationEntity> reservations = reservationDao.findAll();
        return ResponseEntity.ok(reservations.stream()
                .map(ReservationResponse::new)
                .toList());
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(
            @RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());
        long id = reservationDao.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        if (reservationDao.existsById(id)) {
            reservationDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
