package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomEscapeController {

    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;

    public RoomEscapeController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> read() {
        return queryingDAO.findAllReservation();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> add(@RequestBody Reservation reservation) {
        Long id = updatingDAO.add(reservation);
        Reservation newReservation = new Reservation(id, reservation.getName(),
                reservation.getDate(), reservation.getTime());
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        updatingDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
