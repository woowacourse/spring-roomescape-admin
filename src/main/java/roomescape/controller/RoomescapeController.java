package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationDto;
import roomescape.controller.dto.ReservationRegisterDto;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
public class RoomescapeController {

    private final ReservationRepository reservationRepository;

    public RoomescapeController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::toDto)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> registerReservation(
            @RequestBody @Valid final ReservationRegisterDto reservationRegisterDto) {
        try {
            Reservation reservation = reservationRegisterDto.toEntity();
            this.reservationRepository.save(reservation);
            return ResponseEntity.ok().body(ReservationDto.toDto(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable(name = "id") final long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
