package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationController {

    private final ReservationDAO reservationDAO;

    public ReservationController(final ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        final List<Reservation> reservations = reservationDAO.findAllReservation();
        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRequest.toEntity();
        Long id = reservationDAO.insertReservation(reservation);
        if (id == -1) {
            return ResponseEntity.badRequest()
                    .build();
        }
        reservation.setId(id);
        return ResponseEntity.ok()
                .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        int count = reservationDAO.deleteReservationById(id);
        if (count == 0) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.ok()
                .build();
    }
}
