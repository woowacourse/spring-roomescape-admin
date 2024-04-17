package roomescape.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong idCount = new AtomicLong(1);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAll() {
        List<ReservationDto> totalReservations = reservations.values()
                .stream()
                .map(ReservationDto::from)
                .toList();
        return ResponseEntity.ok(totalReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> create(@RequestBody ReservationDto reservationDto) {
        long id = idCount.getAndIncrement();
        Reservation reservation = reservationDto.toEntity(id);
        reservations.put(id, reservation);
        return ResponseEntity.ok(ReservationDto.from(reservation));
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
