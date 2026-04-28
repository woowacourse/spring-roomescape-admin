package roomescape.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<Reservation>> readAll(){
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequestDto reservationRequestDto){
        Reservation requestReservation = reservationRequestDto.toEntity();
        Reservation reservation = Reservation.toEntity(index.incrementAndGet(), requestReservation);

        reservations.add(reservation);

        return ResponseEntity.ok(reservation);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        reservations.removeIf(reservation -> id == reservation.getId());
    }
}
