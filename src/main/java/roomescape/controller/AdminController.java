package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Id;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Controller
public class AdminController {

    private final Reservations reservations = new Reservations();

    @GetMapping("/admin")
    public String displayMain() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String displayAdminReservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDto>> readReservations() {
        List<ReservationResponseDto> reservationResponseDtos = reservations.getAll().stream()
                .map(ReservationResponseDto::of).toList();
        return ResponseEntity.ok().body(reservationResponseDtos);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto reservationRequest) {
        Reservation newReservation = Reservation.toEntity(reservationRequest);
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long idRequest) {
        reservations.deleteById(Id.toEntity(idRequest));
        return ResponseEntity.ok().build();
    }
}
