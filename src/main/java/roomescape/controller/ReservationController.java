package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = List.of(
            new Reservation(1, "브라운", LocalDateTime.parse("2024-04-01T10:00:00")),
            new Reservation(2, "솔라", LocalDateTime.parse("2024-04-01T11:00:00")),
            new Reservation(3, "부리", LocalDateTime.parse("2024-04-02T14:00:00"))
    );

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> findAll() {
        List<ReservationDto> reservationDtos = reservations.stream()
                .map(ReservationDto::from)
                .toList();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationDtos);
    }
}
