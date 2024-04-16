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
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation(index.getAndIncrement());
        reservations.add(reservation);

        return ResponseEntity.ok()
                .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation findedReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservations.remove(findedReservation);

        return ResponseEntity.ok()
                .build();
    }
}
