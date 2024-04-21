package roomescape.reservation;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationApiController {
    private static final int START_INDEX = 1;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(START_INDEX);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = reservationDto.toVo(index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 예약이 없습니다."));
        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
