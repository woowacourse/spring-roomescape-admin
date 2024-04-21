package roomescape.controller;

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
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> reservationResponseDtos = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();

        return ResponseEntity.ok(reservationResponseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation savedReservation = reservationRepository.save(new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time()
        ));

        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponseDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") long id) {
        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
