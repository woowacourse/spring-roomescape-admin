package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @PostConstruct
    void init() {
        reservations.add(new Reservation("이든", "2000-09-07", "10:00"));
        reservations.add(new Reservation("새양", "1998-02-24", "10:00"));
        reservations.add(new Reservation("솔라", "2000-01-01", "10:00"));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationDatum() {
        return ResponseEntity.ok(reservations);
    }
}
