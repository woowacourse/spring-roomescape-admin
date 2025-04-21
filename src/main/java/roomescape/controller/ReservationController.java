package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreationRequest;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationCreationRequest request
    ) {
        if (validatePastDateAndTime(request.getDate(), request.getTime())) {
            return ResponseEntity.badRequest().build();
        }
        Reservation reservation = Reservation.createWithoutId(request.getName(), request.getDate(), request.getTime());
        long id = reservationRepository.add(reservation);

        Optional<Reservation> addedReservation = reservationRepository.findById(id);
        if (addedReservation.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] ID에 해당하는 예약이 존재하지 않습니다.");
        }

        return ResponseEntity
                .created(URI.create("reservations/" + id))
                .body(addedReservation.get());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private boolean validatePastDateAndTime(LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isBefore(now);
    }
}
