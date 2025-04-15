package roomescape;

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
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationDto;

@Controller
public class ApiController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong increment = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservationDtos = reservations.stream().map(ReservationDto::from).toList();
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        Reservation entity = createReservationDto.toEntity(increment.getAndIncrement());
        reservations.add(entity);
        return ResponseEntity.ok(ReservationDto.from(entity));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        Reservation foundReservation = reservations.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservations.remove(foundReservation);
        return ResponseEntity.ok().build();
    }
}
