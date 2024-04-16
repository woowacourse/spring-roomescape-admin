package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<ReservationDto> reservations() {
        return reservations.stream()
                .map(ReservationDto::new)
                .toList();
    }
}
