package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = Arrays.asList(
        Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1),
            LocalTime.of(10, 0)),
        Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2),
            LocalTime.of(11, 0))
    );

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
