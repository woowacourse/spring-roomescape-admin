package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationReq;
import roomescape.dto.ReservationRes;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;

    public ReservationController() {
        this.reservations = new Reservations();
    }

    @GetMapping()
    public ResponseEntity<List<ReservationRes>> reservations() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping()
    public ResponseEntity<ReservationRes> createReservation(@RequestBody final ReservationReq reservationReq) {
        final Reservation reservation = reservationReq.toEntity();
        final long id = reservations.add(reservation);
        return ResponseEntity.ok(ReservationRes.from(id, reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        if (reservations.isExistById(id)) {
            reservations.deleteBy(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
