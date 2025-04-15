package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        Reservation reservation1 = new Reservation(1L, "fora", null, null);
        Reservation reservation2 = new Reservation(2L, "aa", null, null);
        Reservation reservation3 = new Reservation(3L, "bb", null, null);

        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        return ResponseEntity.ok().body(reservations);
    }
}
