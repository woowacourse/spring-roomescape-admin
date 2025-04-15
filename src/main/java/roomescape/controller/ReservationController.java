package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.Reservation;
import roomescape.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>(
            List.of(
                    new Reservation(1, "강산", LocalDate.now(), LocalTime.now()),
                    new Reservation(2, "저스틴", LocalDate.now(), LocalTime.now()),
                    new Reservation(3, "네오", LocalDate.now(), LocalTime.now())
            )
    );

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(ReservationResponse.from(reservations));
    }
}
