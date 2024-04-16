package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class RoomescapeController {

    private final ReservationService reservationService;

    public RoomescapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {

        List<Reservation> reservations = reservationService.getAllReservations();

        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(
            @RequestBody CreateReservationRequest dto) {
        Reservation reservation = reservationService.addReservation(dto);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
