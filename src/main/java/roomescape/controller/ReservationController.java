package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.ReservationDto;
import roomescape.ReservationRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository = ReservationRepository.getInstance();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> reservationResponse = reservations.stream()
                .map(ReservationDto::toDto)
                .toList();

        return ResponseEntity.ok(reservationResponse);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> addReservation(@RequestBody ReservationDto reservationDto) {
        Long savedId = reservationRepository.save(reservationDto.toEntity());
        Reservation savedReservation = reservationRepository.findById(savedId);
        ReservationDto reservationResponse = ReservationDto.toDto(savedReservation);

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
