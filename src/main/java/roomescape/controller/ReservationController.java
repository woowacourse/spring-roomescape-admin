package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationSaveRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse saveReservation(
            @RequestBody final ReservationSaveRequest reservationSaveRequest) {
        final Reservation reservation = reservationSaveRequest.toReservation(id.getAndIncrement());
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(final @PathVariable("id") Long id) {
        final Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));
        reservations.remove(findReservation);
    }
}
