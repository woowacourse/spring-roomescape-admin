package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/admin")
public class ReservationController {

    private final AtomicLong idCount = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String landReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationDto.toEntity(idCount.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservation);
    }
}
