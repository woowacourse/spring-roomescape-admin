package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;
import roomescape.model.Reservations;

@RestController
public class ReservationController {

    private final Reservations reservations;

    public ReservationController(final Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> reservations() {
        Map<Long, Reservation> reservations = this.reservations.getReservations();
        List<ReservationResponseDto> response = reservations.entrySet().stream()
                .map(entry -> new ReservationResponseDto(
                        entry.getKey(),
                        entry.getValue().getName(),
                        entry.getValue().getDate(),
                        entry.getValue().getTime())
                )
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody final ReservationRequestDto requestDto) {
        try {
            ReservationDateTime reservationDateTime = new ReservationDateTime(
                    LocalDateTime.of(requestDto.date(), requestDto.time())
            );

            Long id = reservations.add(new Reservation(requestDto.name(), reservationDateTime));
            Reservation findReservation = reservations.findById(id);

            return ResponseEntity.ok(
                    new ReservationResponseDto(
                            id,
                            findReservation.getName(),
                            findReservation.getDate(),
                            findReservation.getTime()
                    )
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        try {
            reservations.removeById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
