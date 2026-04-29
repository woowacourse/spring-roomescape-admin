package roomescape.controller;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationSchedule;
import roomescape.domain.ReservationTime;
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

        Roomescape roomescape = new Roomescape(new ReservationSchedule(reservationRepository.findAll()));
        Reservation reservation = roomescape.reserve(reservationRequest.name(), new ReservationTime(reservationTime));
        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }
}
