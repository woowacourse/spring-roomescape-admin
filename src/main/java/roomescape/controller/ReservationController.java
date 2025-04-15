package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = initialize();

    private List<Reservation> initialize() {
        Reservation reservation1 = new Reservation(index.getAndIncrement(), "브라운", LocalDateTime.parse("2023-01-01T10:00"));
        Reservation reservation2 = new Reservation(index.getAndIncrement(), "브라운", LocalDateTime.parse("2023-01-02T11:00"));
        return List.of(reservation1, reservation2);
    }

    @GetMapping("/admin/reservation")
    public String showReservationManagementPage() {
        return "/admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> readReservations() {
        List<ReservationDto> dtos = ReservationDto.from(reservations);
        return ResponseEntity.ok(dtos);
    }
}
