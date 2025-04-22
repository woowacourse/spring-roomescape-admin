package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDAO;

    public ReservationController(final ReservationDao reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationDAO.getReservations().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(
            @RequestBody final ReservationRequest reservationRequest
    ) {
        final Reservation reservation = reservationDAO.createReservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());
        return new ReservationResponse(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") final Long id) {
        reservationDAO.deleteReservationById(id);
    }
}
