package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationRequestDTO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationController(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO=reservationTimeDAO;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationTime time = reservationTimeDAO.findReservationTimeById(requestDTO.getTimeId());

        Reservation reservation = new Reservation(requestDTO.getName(), requestDTO.getDate(), time);

        Long generatedId = reservationDAO.insertWithKeyHolder(reservation);

        Reservation newReservation = new Reservation(generatedId, reservation.getName(), reservation.getDate(), time);

        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservationDAO.findAllReservation());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<List<Reservation>> delete(@PathVariable Long id) {
        reservationDAO.delete(id);

        return ResponseEntity.ok().body(reservationDAO.findAllReservation());
    }
}
