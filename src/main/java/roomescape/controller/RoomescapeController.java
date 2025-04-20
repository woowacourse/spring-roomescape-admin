package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.AddReservationDto;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Controller
public class RoomescapeController {
    private final Reservations reservations;

    public RoomescapeController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody AddReservationDto addReservationDto) {
        Reservation newReservation = AddReservationDto.toEntity(reservations.getIndexAndIncrement(), addReservationDto);
        reservations.addReservation(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Reservation oldReservation = reservations.deleteReservation(id);
        return ResponseEntity.ok().body(oldReservation);
    }

}
