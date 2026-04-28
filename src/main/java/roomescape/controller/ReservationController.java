package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations;
    private AtomicLong index = new AtomicLong(1);

    public ReservationController(List <Reservation> reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public Reservation addReservation(@RequestBody Reservation reservation) {

        Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(newReservation);
        return newReservation;
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public Reservation deleteReservation(@PathVariable Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(deleteReservation);

        return deleteReservation;
    }
}
