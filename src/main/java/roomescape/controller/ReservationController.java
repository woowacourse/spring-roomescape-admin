package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.CreateReservationDto;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String getAdminHomepage() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String getAdminReservationPage() {
        return "admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@RequestBody CreateReservationDto reservationDto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationDto.name(), reservationDto.date(), reservationDto.time());
        reservations.add(reservation);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservations.removeIf(reservation -> Objects.equals(reservation.id(), id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
