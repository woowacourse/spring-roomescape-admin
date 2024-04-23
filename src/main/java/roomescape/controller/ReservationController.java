package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.vo.Reservation;
import roomescape.data.dto.ReservationDto;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationRepository.getAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reserve(@RequestBody final ReservationDto reservationDto) {
        Reservation reservation = new Reservation.Builder()
                .name(reservationDto.getName())
                .date(reservationDto.getDate())
                .time(reservationDto.getTime())
                .build();

        final var savedId = reservationRepository.add(reservation);

        final var httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/reservations/" + savedId));

        return ResponseEntity.status(CREATED)
                .headers(httpHeaders)
                .body(new Reservation(savedId, reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        reservationRepository.remove(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
