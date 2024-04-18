package roomescape.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.admin.reservation.RequestReservation;
import roomescape.admin.reservation.Reservation;
import roomescape.admin.reservation.ResponseReservation;

@Controller
public class AdminController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }


    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ResponseReservation>> findAll() {
        List<ResponseReservation> responseReservations = reservations.stream()
                .map(reservation -> new ResponseReservation(reservation.getId(), reservation.getName(),
                        reservation.getDate(), reservation.getTime()))
                .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ResponseReservation> create(@RequestBody RequestReservation requestReservation) {
        Reservation reservation = new Reservation(atomicLong.incrementAndGet(), requestReservation.name(),
                requestReservation.date(), requestReservation.time());
        reservations.add(reservation);

        return ResponseEntity.ok(
                new ResponseReservation(reservation.getId(), reservation.getName(), reservation.getDate(),
                        reservation.getTime()));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .ifPresent(reservations::remove);

        return ResponseEntity.ok().build();
    }
}
