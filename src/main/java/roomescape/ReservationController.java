package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("admin/reservation")
    public String showReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok()
                             .body(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody final ReservationDto reservationDto) {
        Reservation reservation = Reservation.toEntity(index.getAndIncrement(), reservationDto);
        reservations.add(reservation);
        return ResponseEntity.ok()
                             .body(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        final Reservation reservation = reservations.stream()
                                                    .filter(it -> Objects.equals(it.getId(), id))
                                                    .findFirst()
                                                    .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
        return ResponseEntity.ok()
                             .build();
    }
}
