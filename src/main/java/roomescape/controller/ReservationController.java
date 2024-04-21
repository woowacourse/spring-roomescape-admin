package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;


    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationDatum() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservationData(@RequestBody final Reservation request) {
        Reservation reservation = reservationRepository.insert(request);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);


    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        int updateCount = reservationRepository.deleteById(id);

        if (updateCount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
