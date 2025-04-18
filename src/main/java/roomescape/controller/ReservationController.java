package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
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
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    private final List<ReservationResponse> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);


    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> findAll() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationCreateRequest reservationCreateRequest) {

        Reservation reservation = reservationCreateRequest.dtoToReservation();
        Reservation reservationWithId = Reservation.createReservationWithId(index.getAndIncrement(), reservation);

        ReservationResponse returnDto = ReservationResponse.reservationToDto(reservationWithId);
        reservations.add(returnDto);
        return ResponseEntity.created(URI.create("/reservations/" + index.get())).body(returnDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ReservationResponse reservation = reservations.stream()
                .filter(it -> Objects.equals(it.id(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
