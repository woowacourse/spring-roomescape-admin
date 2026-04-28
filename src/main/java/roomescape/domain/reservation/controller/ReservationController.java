package roomescape.domain.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.response.ReservationResponse;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("reservation이 null 입니다.");
        }
        reservation.setId(index.getAndIncrement());
        reservations.add(reservation);

        ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, String.valueOf(response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long reservationId) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 reservation id 입니다. id=" + reservationId));

        reservations.remove(findReservation);

        return ResponseEntity.ok().build();
    }
}
