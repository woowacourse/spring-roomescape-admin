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
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;
import roomescape.domain_entity.Reservations;

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
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationRequestDto reservationRequest
    ) {
        Reservation newReservation = reservationRequest.toEntity();
        reservations.add(newReservation);
        return ResponseEntity.ok().body(ReservationResponseDto.of(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteReservation(
            @PathVariable("id") long idRequest
    ) {
        try {
            reservations.deleteById(new Id(idRequest));
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
