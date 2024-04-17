package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {
    private List<Reservation> reservations = List.of(new Reservation((long) 1,"a", LocalDate.now(),LocalTime.now()));
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong id = new AtomicLong(1);

    @GetMapping("admin")
    public String admin() {
        return "/admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = reservationDto.toEntity(id.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

}
