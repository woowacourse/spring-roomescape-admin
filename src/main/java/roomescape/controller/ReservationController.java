package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationCreateResponse;
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;
import roomescape.service.GameTimeService;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;
    private final GameTimeService gameTimeService;

    public ReservationController(ReservationService reservationService, GameTimeService gameTimeService) {
        this.reservationService = reservationService;
        this.gameTimeService = gameTimeService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationCreateResponse>> readAllReservations() {
        List<ReservationCreateResponse> reservations = reservationService.readAll()
                .stream()
                .map(ReservationCreateResponse::from)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @RequestBody ReservationCreateRequest reservationCreateRequest) {
        GameTime time = gameTimeService.findById(reservationCreateRequest.getTimeId());
        Reservation savedReservation = reservationService.saveReservation(reservationCreateRequest.toEntity(time));
        return ResponseEntity.ok().body(ReservationCreateResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
