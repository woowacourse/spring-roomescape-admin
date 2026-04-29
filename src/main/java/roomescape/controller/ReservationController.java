package roomescape.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.persistence.dao.ReservationDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationDao.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservations(@RequestBody Reservation reservation) {
        Long id = reservationDao.insert(reservation);
        Reservation reservationById = reservationDao.findById(id);
        return ResponseEntity.ok(reservationById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        int delete = reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
