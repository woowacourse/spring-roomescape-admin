package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.domain.Roomescape;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class RoomescapeApiController {

    private final ReservationRepository reservationRepository;

    public RoomescapeApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest reservationRequest) {
        LocalDateTime reservationTime = LocalDateTime.of(reservationRequest.date(), reservationRequest.time());

        Roomescape roomescape = new Roomescape(reservationRepository.findAll());
        Reservation reservation = roomescape.reserve(reservationRequest.name(), reservationTime);
        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> response = reservationRepository.findAll()
                .getSchedule()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        Roomescape roomescape = new Roomescape(reservationRepository.findAll());
        roomescape.cancelReservation(id);
        reservationRepository.saveAll(roomescape.getSchedule());
        return ResponseEntity.ok().build();
    }
}
