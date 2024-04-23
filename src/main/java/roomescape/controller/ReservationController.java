package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.dto.ReservationDto;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> reservationResponse = reservations.stream()
                .map(ReservationDto::toDto)
                .toList();

        return ResponseEntity.ok(reservationResponse);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> addReservation(@RequestBody ReservationDto reservationRequest) {
        Long savedId = reservationRepository.save(reservationRequest.toEntity());
        String redirectUrl = "/reservations/" + savedId;
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", redirectUrl).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
