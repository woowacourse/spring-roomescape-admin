package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> find() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody final ReservationRequestDto request) {
        final LocalDate date = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalTime time = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

        final Reservation reservation = new Reservation(request.getName(), date, time);
        final Long id = reservationRepository.save(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + id))
                .body(new ReservationResponseDto(id, reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
