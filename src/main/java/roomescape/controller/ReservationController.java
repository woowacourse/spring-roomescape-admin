package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.dto.response.ReservationCreateResponse;

@Controller
public class ReservationController {

    private final List<ReservationCreateResponse> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationCreateResponse>> getReservations() {
        ReservationCreateResponse reservation1 = new ReservationCreateResponse(1L, "fora", null, null);
        ReservationCreateResponse reservation2 = new ReservationCreateResponse(2L, "aa", null, null);
        ReservationCreateResponse reservation3 = new ReservationCreateResponse(3L, "bb", null, null);

        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        return ResponseEntity.ok().body(reservations);
    }
}
