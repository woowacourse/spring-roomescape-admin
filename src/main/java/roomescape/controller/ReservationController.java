package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
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
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        Reservations reservations = reservationDao.findAll();
        return reservations.getReservations().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        Reservation newReservation = request.toReservation(null);
        Reservation savedReservation = reservationDao.save(newReservation);

        return new ReservationResponse(savedReservation);
    }

    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable Long id, HttpServletResponse response) {
        boolean isDeleted = reservationDao.deleteById(id);

        if (!isDeleted) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
