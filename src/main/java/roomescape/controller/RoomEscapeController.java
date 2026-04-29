package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.service.RoomEscapeService;

@RestController
public class RoomEscapeController {

    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return roomEscapeService.findAll();
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationCreateRequest request) {
        return roomEscapeService.create(request.name(), request.date(), request.time());
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable final long id) {
        roomEscapeService.delete(id);
    }
}
