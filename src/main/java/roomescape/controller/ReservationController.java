package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public List<Reservation> findAll() {
        return reservations;
    }

    @PostMapping("/reservations")
    public Map<String, Long> post(@RequestBody Map<String, String> params) {
        String name = params.get("name");
        String date = params.get("date");
        String time = params.get("time");

        Reservation reservation = new Reservation(index.incrementAndGet(), name, date, time);
        reservations.add(reservation);
        return Map.of("id", reservation.getId());
    }

    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        reservations.removeIf(r -> r.getId().equals(id));
    }
}
