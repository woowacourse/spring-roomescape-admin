package roomescape.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservationResponses = reservationDao.findAllReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();

        Long id = reservationDao.saveReservation(reservation);
        Reservation saveReservation = reservationDao.findReservation(id);
        ReservationResponse reservationResponse = ReservationResponse.from(saveReservation);

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
