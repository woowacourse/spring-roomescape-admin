package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.SaveReservationRequest;
import roomescape.service.ReservationCreateService;
import roomescape.service.ReservationFindService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationFindService reservationFindService;
    private final ReservationCreateService reservationCreateService;

    public ReservationController(ReservationFindService reservationFindService, ReservationCreateService reservationCreateService) {
        this.reservationFindService = reservationFindService;
        this.reservationCreateService = reservationCreateService;
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationFindService.findReservations();
        return ResponseEntity.ok(ReservationResponse.listOf(reservations));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody SaveReservationRequest request) {
        Reservation newReservation = reservationCreateService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(ReservationResponse.of(newReservation));
    }
}
