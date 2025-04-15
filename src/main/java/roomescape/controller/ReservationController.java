package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.dto.response.ReservationDto;

@Controller
public class ReservationController {

    private final List<ReservationDto> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        ReservationDto reservation1 = new ReservationDto(1L, "fora", null, null);
        ReservationDto reservation2 = new ReservationDto(2L, "aa", null, null);
        ReservationDto reservation3 = new ReservationDto(3L, "bb", null, null);

        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        return ResponseEntity.ok().body(reservations);
    }
}
