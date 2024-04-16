package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody CreateReservationRequest request) {
        Long id = index.getAndIncrement();
        Reservation reservation = request.toReservation(id);
        reservations.add(reservation);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        reservations.remove(reservation);

        return ResponseEntity.ok().build();
    }
}
