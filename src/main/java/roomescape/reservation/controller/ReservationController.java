package roomescape.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(
            List.of(new Reservation(1, "대니", LocalDate.of(2025,4,4), LocalTime.of(3,30)),
                    new Reservation(2, "대니", LocalDate.of(2025,4,4), LocalTime.of(3,30)),
                    new Reservation(3, "대니", LocalDate.of(2025,4,4), LocalTime.of(3,30))
            )
    );

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

}
