package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationRegister;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class RoomescapeController {

    private final ReservationRepository reservationRepository;

    public RoomescapeController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> registerReservation(
            @RequestBody @Valid final ReservationRegister reservationRegister) {
        try {
            Reservation reservation = reservationRegister.toEntity();
            this.reservationRepository.save(reservation);
            return ResponseEntity.ok().body(ReservationResponse.toDto(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") final long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
