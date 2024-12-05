package roomescape.controller;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.service.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Map<Long, Reservation> reservations = new HashMap<>();

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservations.values()
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostConstruct
    public void init() {
        reservations.put(1L, new Reservation(1L, "브라운", LocalDate.of(2024, 12, 25), LocalTime.of(10, 0)));
        reservations.put(2L, new Reservation(2L, "솔라", LocalDate.of(2024, 12, 25), LocalTime.of(11, 0)));
        reservations.put(3L, new Reservation(3L, "브리", LocalDate.of(2024, 12, 25), LocalTime.of(14, 0)));
    }
}
