package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.service.ReservationMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservationDatum() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationMapper::map)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservationData(@RequestBody final ReservationRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = ReservationMapper.map(request).assignId(id);
        reservations.add(reservation);

        ReservationResponse response = ReservationMapper.map(reservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.id().equals(id));
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
