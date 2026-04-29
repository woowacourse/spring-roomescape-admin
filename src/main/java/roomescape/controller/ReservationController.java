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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.service.ReservationService;
import roomescape.service.command.ReservationCreateCommand;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(
            @RequestBody ReservationCreateRequest createRequest
    ) {
        ReservationCreateCommand createCommand = new ReservationCreateCommand(
                createRequest.name(),
                createRequest.date(),
                createRequest.timeId()
        );
        Reservation createdReservation = reservationService.create(createCommand);

        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservations = reservationService.findAll();

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {
        reservationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
