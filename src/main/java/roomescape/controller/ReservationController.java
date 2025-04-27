package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<ReservationResponse> readReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        Reservation reservationWithId = reservationDao.insert(reservation, reservationRequest.timeId());
        return ReservationResponse.from(reservationWithId);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable(name = "id") long id) {
        reservationDao.deleteById(id);
    }
}
