package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservationDao.findAll().stream()
                .map(ReservationResponse::fromReservation)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Long> createReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Long savedId = reservationDao.save(reservationCreateRequest);
        return ResponseEntity.created(URI.create("/reservations/" + savedId)).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long reservationId) {
        reservationDao.delete(reservationId);
        return ResponseEntity.noContent().build();
    }
}
