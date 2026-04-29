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
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.mapper.ReservationMapper;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.service.ReservationService;
import roomescape.service.command.ReservationCreateCommand;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(
            ReservationService reservationService,
            ReservationMapper reservationMapper
    ) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationCreateRequest createRequest
    ) {
        ReservationCreateCommand createCommand = reservationMapper.mapCreateToCommand(createRequest);
        Reservation createdReservation = reservationService.create(createCommand);
        ReservationResponse reservationResponse = reservationMapper.mapToResponse(createdReservation);

        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservations = reservationService.findAll()
                .stream()
                .map(reservationMapper::mapToResponse)
                .toList();

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
