package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import roomescape.ReservationDto;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String home() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok()
                .body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDto reservationDto){

        Reservation reservation = new Reservation(index.getAndDecrement(), reservationDto.name(),reservationDto.date(),reservationDto.time());

        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id){
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        reservations.remove(deleteReservation);

        return ResponseEntity.ok().build();
    }
}
