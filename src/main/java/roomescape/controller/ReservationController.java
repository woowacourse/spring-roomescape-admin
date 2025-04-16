package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @GetMapping("/admin")
    public String getMainPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readReservation() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public Reservation postReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return newReservation;
    }
}
