package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.reservationRepository = new ReservationRepository(jdbcTemplate);
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public Reservation createReservation(
            @RequestBody CreateReservationDto createReservationDto) {
        Reservation reservation = reservationRepository.add(createReservationDto);
        return reservation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
