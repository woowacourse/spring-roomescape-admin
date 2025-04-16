package roomescape.controller;

import roomescape.dto.ReservationDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDto reservationDto) {
        //RequestBody와 ModelAttribute 차이 알아보기
        Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationDto.getTime());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            Reservation findReservation = reservations.stream()
                    .filter(reservation -> reservation.getId() == id)
                    .findAny()
                    .orElseThrow(RuntimeException::new);
            reservations.remove(findReservation);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
