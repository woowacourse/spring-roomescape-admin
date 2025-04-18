package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserRoomEscapeController {

    private static final AtomicLong autoIncrement = new AtomicLong(0);
    private static final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservations(@RequestBody ReservationCreateRequest request) {
        Reservation newReservation = request.toDomain(autoIncrement.incrementAndGet());

        reservations.add(newReservation);

        ReservationResponse response = ReservationResponse.from(newReservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservations(@PathVariable("reservationId") Long reservationId) {
        Reservation target = getReservation(reservationId);

        reservations.remove(target);

        return ResponseEntity.ok().build();
    }

    private static Reservation getReservation(Long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.hasSameId(reservationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 예약이 존재하지 않습니다."));
    }

    public static void clear() {
        reservations.clear();
        autoIncrement.set(0);
    }
}
