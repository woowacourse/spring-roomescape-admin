package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> create(@RequestBody ReservationCreateRequest request) {
        Reservation newReservation = new Reservation(index.get(), request.name(), request.date(), request.time());
        index.getAndIncrement();
        reservations.add(newReservation);

        ReservationCreateResponse response = new ReservationCreateResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime());
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(response);
    }
}
