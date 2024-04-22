package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        long id = reservationService.addReservation(reservationDto);
        Reservation reservation = makeReservation(reservationDto, id);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        reservationService.deleteReservationWithId(id);
        return ResponseEntity.noContent().build();
    }

    private Reservation makeReservation(ReservationDto reservationDto, long id) {
        String name = reservationDto.name();
        String date = reservationDto.date();
        String time = reservationDto.time();
        return new Reservation(id, name, date, time);
    }
}
