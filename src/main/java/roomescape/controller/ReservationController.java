package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private Reservations reservations = new Reservations();
    private final ReservationService reservationService;
    private AtomicLong index = new AtomicLong(1);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> allReservation = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservation);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        try {
            ReservationDateTime reservationDateTime = new ReservationDateTime(
                    LocalDateTime.of(reservationRequestDto.date(), reservationRequestDto.time())
            );

            Reservation newReservation = new Reservation(
                    index.getAndIncrement(),
                    reservationRequestDto.name(),
                    reservationDateTime);
            reservations.add(newReservation);
            reservationService.saveReservation(reservationRequestDto);
            return ResponseEntity.ok(ReservationResponseDto.from(newReservation));
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservations.removeById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
