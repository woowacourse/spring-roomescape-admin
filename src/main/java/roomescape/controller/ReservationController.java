package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.repository.ReservationDAO;
import roomescape.repository.ReservationTimeDAO;

@Controller
public class ReservationController {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationController(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationDAO.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeDAO.findById(createReservationRequest.timeId());
        Reservation newReservation = new Reservation(
                null,
                createReservationRequest.name(),
                createReservationRequest.date(),
                reservationTime
        );
        Long newReservationId = reservationDAO.save(newReservation);

        Reservation createdReservation = reservationDAO.findById(newReservationId);

        return ResponseEntity.ok().body(createdReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
