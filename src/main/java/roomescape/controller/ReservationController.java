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
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
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
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Reservation reservation = reservationDao.insert(
                new Reservation(0L, request.name(), request.date(), reservationTime));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReservationResponse(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
