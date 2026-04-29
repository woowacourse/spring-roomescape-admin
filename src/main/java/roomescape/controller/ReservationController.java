package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservation() {
        List<ReservationResponseDto> responseDtoList = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation newReservation = reservationRequestDto.toEntity(index.incrementAndGet());
        reservations.add(newReservation);
        return ResponseEntity.ok(ReservationResponseDto.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                reservations.remove(reservation);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

}
