package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDAO;
import roomescape.model.ReservationTime;

import java.util.List;

@Controller
public class ReservationTimeController {
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeController(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime){
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);
        return ResponseEntity.ok(reservationTimeDAO.findReservationTimeById(id));
    }

    @GetMapping("times")
    public ResponseEntity<List<ReservationTime>> read(){
        return ResponseEntity.ok(reservationTimeDAO.findAllReservationTimes());
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reservationTimeDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
