package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> findAll() {
        List<ReservationDto> reservationDtos = reservations.stream()
                .map(ReservationDto::from)
                .toList();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> add(@RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ReservationDto.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .ifPresent(reservations::remove);

        return ResponseEntity.ok().build();
    }
}
