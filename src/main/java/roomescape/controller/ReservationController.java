package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.QueryingDAO;
import roomescape.model.Reservation;
import roomescape.model.UpdatingDAO;

import java.util.List;


@Controller
public class ReservationController {
    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;

    public ReservationController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }


    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(queryingDAO.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        Long id = updatingDAO.insertWithKeyHolder(newReservation);
        return ResponseEntity.ok(queryingDAO.findReservationById(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        updatingDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
