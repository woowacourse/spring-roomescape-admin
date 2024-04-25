package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationRestController {
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationRestController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        return reservationDao.getReservations().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservationInfo(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationDao.add(reservationRequest);
        return ResponseEntity.created(URI.create("reservations/" + reservation.getId()))
                .body(new ReservationResponse(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationInfo(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
