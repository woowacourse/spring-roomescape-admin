package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findReservations() {
        List<ReservationDto> reservations = reservationRepository.findAll().stream()
                .map(ReservationDto::from)
                .toList();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRepository.save(reservationRequest.toReservation());
        return ResponseEntity.created(URI.create("/admin/reservation"))
                .body(ReservationDto.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "id") Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
