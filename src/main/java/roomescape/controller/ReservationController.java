package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationCreateRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations() {
        List<Reservation> reservations = reservationDao.readAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest request) {
        int createdReservationId = reservationDao.create(request.toReservation());
        return ResponseEntity.created(URI.create("/reservations/" + createdReservationId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
