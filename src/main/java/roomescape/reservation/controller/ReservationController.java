package roomescape.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationServiceImpl;

    public ReservationController(ReservationService reservationService) {
        this.reservationServiceImpl = reservationService;
    }

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "/admin/reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        List<ReservationResponseDto> responseDtos = reservationServiceImpl.getAll();

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> save(@RequestBody ReservationRequestDto requestDto) {
        ReservationResponseDto responseDto = reservationServiceImpl.save(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}
