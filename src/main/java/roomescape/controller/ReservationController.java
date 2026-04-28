package roomescape.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return new ResponseEntity<>(Collections.unmodifiableList(reservations), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(
        @RequestBody ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = Reservation.from(index.incrementAndGet(),
            reservationRequestDto);
        reservations.add(reservation);

        return new ResponseEntity<>(ReservationResponseDto.from(reservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
