package roomescape.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;
import roomescape.dto.AddReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidReservationException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {
        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::fromReservation)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> add(@RequestBody final AddReservationRequest addReservationRequest) {
        final Reservation addReservation = addReservationRequest.toReservation(index.getAndIncrement());
        reservations.add(addReservation);
        final ReservationResponse reservationResponse = ReservationResponse.fromReservation(addReservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        final Reservation deleteReservation = findReservationById(id);
        reservations.remove(deleteReservation);
        return ResponseEntity.ok().build();
    }

    private Reservation findReservationById(final Long id) {
        return reservations.stream()
                .filter((reservation) -> reservation.id().equals(id))
                .findAny()
                .orElseThrow(() -> new InvalidReservationException("해당 예약 번호의 예약을 찾을 수 없습니다."));
    }
}
