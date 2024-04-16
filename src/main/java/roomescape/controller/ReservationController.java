package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok().body(reservationResponses);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
