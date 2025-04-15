package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String home() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservations() {
        reservations.add(new Reservation(1L, "name", LocalDate.now(), LocalTime.now()));
        return ResponseEntity.ok()
                .body(reservations);
    }
}
