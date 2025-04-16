package roomescape.controller;

import java.time.Clock;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationInfo;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationController(final ReservationRepository reservationRepository, final Clock clock) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservationResponses = reservationRepository.getReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final CreateReservationRequest createReservationRequest
    ) {
        ReservationInfo reservationInfo = createReservationRequest.toReservationInfo(clock);
        Reservation savedReservation = reservationRepository.add(reservationInfo);
        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        if (!reservationRepository.existReservation(id)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
