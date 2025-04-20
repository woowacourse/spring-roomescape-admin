package roomescape.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreationRequest;
import roomescape.dto.ReservationCreationResponse;
import roomescape.repository.ReservationRepository;

@Controller
public class RoomescapeController {

    @Autowired
    private final ReservationRepository reservationRepository;

    public RoomescapeController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreationResponse> createReservation(
            @RequestBody ReservationCreationRequest input
    ) {
        try {
            LocalDateTime dateTime = LocalDateTime.of(input.getDate(), input.getTime());
            LocalDateTime now = LocalDateTime.now();
            if (dateTime.isBefore(now)) {
                throw new IllegalArgumentException("과거의 날짜와 시간으로 예약을 생성할 수 없습니다.");
            }

            long id = reservationRepository.add(input.getName(), input.getDate(), input.getTime());
            return ResponseEntity
                    .created(URI.create("reservations/" + id))
                    .body(new ReservationCreationResponse(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
