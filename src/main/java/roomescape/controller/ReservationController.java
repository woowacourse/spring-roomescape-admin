package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservation() {
        List<ReservationResponseDto> responseDtoList = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public String createReservation() {
        return "temp";
    }

    @DeleteMapping("/{id}")
    public String deleteReservation(@PathVariable Long id) {
        return "temp";
    }

}
