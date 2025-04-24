package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.service.ReservationService;

@RestController
public class ReservationCommandController {

    @Autowired
    private final ReservationService reservationService;

    private ReservationCommandController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        List<Reservation> reservations = reservationService.readAll();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("reservations/{reservationId}")
    public ResponseEntity<Reservation> readReservation(@PathVariable("reservationId") Long id) {
        Reservation reservation = reservationService.readOne(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequestDto reservationDto) {
        Long id = reservationService.add(reservationDto);
        Reservation reservation = reservationService.readOne(id);
        String location = "/reservations/" + id;
        return ResponseEntity.created(URI.create(location)).body(reservation);
    }

    @DeleteMapping("reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
