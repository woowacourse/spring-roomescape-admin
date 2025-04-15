package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(1L, "hotteok", LocalDate.now(), LocalTime.now()),
            new Reservation(2L, "norang", LocalDate.now(), LocalTime.now()),
            new Reservation(3L, "neo", LocalDate.now(), LocalTime.now())
    ));

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> reservationDtos = reservations.stream()
                .map(reservation -> new ReservationDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();

        return ResponseEntity.ok(reservationDtos);
    }
}
