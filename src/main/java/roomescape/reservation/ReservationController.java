package roomescape.reservation;

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
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.Reservations;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1L);
    private final Reservations reservations;

    public ReservationController() {
        this.reservations = new Reservations(new ArrayList<>());
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());
        reservations.addReservation(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> getReservations(@PathVariable("id") long id) {
        if (reservations.deleteById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
