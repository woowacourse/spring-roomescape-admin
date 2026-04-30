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
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDAO;

@Controller
public class ReservationTimeController {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeController(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAll();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long newReservationTimeId = reservationTimeDAO.save(reservationTime);
        ReservationTime createdReservationTime = reservationTimeDAO.findById(newReservationTimeId);
        return ResponseEntity.ok().body(createdReservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
