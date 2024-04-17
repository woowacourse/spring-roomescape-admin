package roomescape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private Map<Long, Reservation> reservations = new HashMap<>();
    private AtomicLong index = new AtomicLong();

    @GetMapping
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        long id = index.incrementAndGet();
        Reservation reservation = new Reservation(id, request.getName(),
                request.getDate(), request.getTime());
        reservations.put(id, reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Model model) {
        reservations.remove(id);
        model.addAttribute(id);
        return ResponseEntity.ok().build();
    }
}
