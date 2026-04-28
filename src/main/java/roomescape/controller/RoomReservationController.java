package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.RoomReservation;
import roomescape.service.RoomReservationService;

@RestController
@RequestMapping("/reservations")
public class RoomReservationController {

    private final RoomReservationService roomReservationService;

    public RoomReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public ResponseEntity<List<RoomReservation>> getRoomReservations() {
        List<RoomReservation> reservations = roomReservationService.findAllRoomReservations();
        return ResponseEntity.ok(reservations);
    }
}
