package roomescape.controller;

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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@RestController
public class RoomescapeApiController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public RoomescapeApiController(final ReservationService reservationService,
                                   final ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Optional<ReservationTime> reservationTimeOptional = reservationTimeService.findById(request.timeId());
        if (reservationTimeOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Reservation reservation = new Reservation(request.name(), request.date(), reservationTimeOptional.get());
        long savedId = reservationService.addReservation(reservation);
        if (savedId > 0) {
            return ResponseEntity.ok(ReservationResponse.from(reservation.withId(savedId)));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable long id) {
        boolean removed = reservationService.removeReservationById(id);
        if (removed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
