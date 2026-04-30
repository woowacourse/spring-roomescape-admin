package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Controller
public class ReservationTimeController {

    @Autowired
    private ReservationTimeDAO reservationTimeDAO;

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime saved = reservationTimeDAO.insert(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @ResponseBody
    @GetMapping("/times")
    public List<ReservationTime> findAll() {
        return reservationTimeDAO.findAll();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
