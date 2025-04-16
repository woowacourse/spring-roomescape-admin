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
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.AddReservationDto;
import roomescape.exception.InvalidReservationRequest;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("")
    public ResponseEntity<Void> addReservations(@RequestBody AddReservationDto addReservationDto) {
        if(addReservationDto == null){
            throw new InvalidReservationRequest();
        }

        Reservation newReservation = new Reservation(index.getAndIncrement(), addReservationDto.name(),
                addReservationDto.date(), addReservationDto.time());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter((reservation) -> reservation.id().equals(id))
                .findAny()
                .orElseThrow(InvalidReservationRequest::new);

        reservations.remove(deleteReservation);
        return ResponseEntity.noContent().build();
    }
}
