package roomescape.reservation.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dao.ReservationDAO;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationAllResponse;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationIDResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationController(final ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping
    public List<ReservationAllResponse> getReservations() {
        List<Reservation> reservations = reservationDAO.findAllReservations();

        return reservations.stream()
                .map(reservation -> new ReservationAllResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                )).toList();
    }


    @PostMapping
    public ReservationIDResponse createReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {
        long insertedId = reservationDAO.insertReservation(reservationRequest);
        return new ReservationIDResponse(insertedId);
    }

    @DeleteMapping("/{id}")
    public void deleteReservations(
            @PathVariable("id") long id
    ) {
        reservationDAO.removeReservation(id);
    }
}
