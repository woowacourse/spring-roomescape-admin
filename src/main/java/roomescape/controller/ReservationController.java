package roomescape.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    @Autowired
    private ReservationDAO reservationDAO;

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody Reservation reservation) {
        reservationDAO.insert(reservation);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> findAll() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
