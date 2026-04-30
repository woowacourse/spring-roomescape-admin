package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

import java.util.List;

@Controller
public class ReservationTimeController {
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeController(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long generatedId = reservationTimeDAO.insertWithKeyHolder(reservationTime);

        ReservationTime newReservationTime = ReservationTime.toEntity(reservationTime, generatedId);

        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        return ResponseEntity.ok().body(reservationTimeDAO.findAllReservationTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<List<ReservationTime>> delete(@PathVariable Long id) {
        reservationTimeDAO.delete(id);

        return ResponseEntity.ok().body(reservationTimeDAO.findAllReservationTime());
    }
}
