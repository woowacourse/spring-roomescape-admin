package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(ReservationResponse.listOf(reservations));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody SaveReservationRequest request) {
        Reservation newReservation = SaveReservationRequest.toEntity(index.getAndIncrement(), request);
        reservations.add(newReservation);
        return ResponseEntity.ok(ReservationResponse.of(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservationToDeleted = reservations.stream()
                .filter(reservation -> reservation.isSameReservation(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 아이디 입니다."));

        reservations.remove(reservationToDeleted);

        return ResponseEntity.ok().build();
    }
}
