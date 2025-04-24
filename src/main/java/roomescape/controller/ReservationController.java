package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationService;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping()
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        return ResponseEntity.ok(reservationService.addReservation(reservationRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable long id) {
        return ResponseEntity.ok().body(reservationService.deleteReservation(id));
    }

}
