package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong idCount = new AtomicLong(1);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> totalReservations = reservations.values()
                .stream()
                .toList();
        return ResponseEntity.ok(totalReservations);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationDto.toEntity(idCount.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("id에 해당하는 예약을 찾을 수 없습니다.");
        }
        reservations.remove(id);
        return ResponseEntity.ok().build();
    }
}
