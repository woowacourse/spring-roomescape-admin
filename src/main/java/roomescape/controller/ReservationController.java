package roomescape.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationDao;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservationResponses = reservationDao.findAll()
                .stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationCreateRequest request) {
        Long reservationId = reservationDao.insert(new Reservation(0L, request.name(), request.date(), request.time()));
        Reservation reservation = reservationDao.findById(reservationId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReservationResponse(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
