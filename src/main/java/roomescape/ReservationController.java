package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );

        reservations.add(reservation);

        return ResponseEntity.ok(
                new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                )
        );
    }
}
