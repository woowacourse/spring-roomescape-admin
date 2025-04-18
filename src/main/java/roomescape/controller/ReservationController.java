package roomescape.controller;

import java.net.URI;
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

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::fromReservation)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("")
    public ResponseEntity<Void> addReservations(@RequestBody AddReservationRequest addReservationRequest) {
        if (addReservationRequest == null) {
            throw new InvalidReservationException("예약을 추가할 수 없습니다.");
        }

        Reservation newReservation = addReservationRequest.toReservation(index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter((reservation) -> reservation.id().equals(id))
                .findAny()
                .orElseThrow(() -> new InvalidReservationException("존재하지 않는 예약 번호를 삭제할 수 없습니다."));

        reservations.remove(deleteReservation);
        return ResponseEntity.noContent().build();
    }
}
