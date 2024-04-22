package roomescape.controller;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.repository.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return reservationDao.findAll()
            .stream()
            .map(ReservationResponse::from)
            .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservations(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = ReservationRequest.from(reservationRequest);
        Reservation savedReservation = reservationDao.save(reservation);
        ReservationResponse reservationResponse = ReservationResponse.from(savedReservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.id()))
            .body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
