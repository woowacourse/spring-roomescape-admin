package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;

    public ReservationsController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = queryingDAO.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Long id = updatingDAO.insert(reservation);
        return ResponseEntity.ok().body(Reservation.toEntity(reservation, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        updatingDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}

