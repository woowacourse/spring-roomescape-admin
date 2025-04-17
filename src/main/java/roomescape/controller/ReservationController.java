package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationController {

    private final ReservationRepository repository;

    @Autowired
    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservations")
    public List<Reservation> readReservation() {
        return repository.findAll();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto postReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation newReservation = repository.save(requestDto.toEntity(null));
        return ReservationResponseDto.toDto(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            repository.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
