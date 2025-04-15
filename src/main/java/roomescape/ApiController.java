package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.dto.ReservationDto;

@Controller
public class ApiController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong increment = new AtomicLong(1);

    private void generateData() {
        for (int i = 0; i < 3; ++i) {
            reservations.add(new Reservation(
                    increment.getAndIncrement(),
                    i + "",
                    LocalDate.now(),
                    LocalTime.now()
            ));
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        generateData();
        List<ReservationDto> reservationDtos = reservations.stream().map(ReservationDto::from).toList();
        return ResponseEntity.ok(reservationDtos);
    }
}
