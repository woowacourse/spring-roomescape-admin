package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ReservationController {

    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;

    public ReservationController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Long generatedId = updatingDAO.insertWithKeyHolder(reservation);
        Reservation newReservation = queryingDAO.findReservationById(generatedId);
        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = queryingDAO.findAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Void> update(@RequestBody Reservation newReservation, @PathVariable Long id) {
        Reservation reservation = queryingDAO.findReservationById(id);
        reservation.update(newReservation);
        updatingDAO.save(id, reservation);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int count = updatingDAO.delete(id);

        if (count == 0) {
            throw new RuntimeException("삭제하려는 예약을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok().build();
    }
}
