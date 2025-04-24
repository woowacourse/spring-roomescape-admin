package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dao.ReservationDAO;
import roomescape.reservation.dao.ReservationTimeDAO;
import roomescape.reservation.dto.ReservationReqDTO;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationController(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationReqDTO dto) {
        ReservationTime reservationTime = reservationTimeDAO.selectBy(dto.timeId());
        Reservation reservationInfo = dto.toEntityWith(reservationTime);
        Reservation newReservation = reservationDAO.insert(reservationInfo);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        return ResponseEntity.ok(reservationDAO.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getBy(@PathVariable Long id) {
        Reservation reservation = reservationDAO.selectBy(id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        try {
            reservationDAO.deleteBy(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
