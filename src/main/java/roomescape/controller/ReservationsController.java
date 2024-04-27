package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.service.RoomescapeService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final RoomescapeService roomescapeService;

    public ReservationsController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping
    public List<ReservationsResponse> reservations() {
        return roomescapeService.finaAllReservations();
    }

    @PostMapping
    public ReservationsResponse addReservation(@RequestBody ReservationsRequest reservationsRequest) {
        return roomescapeService.addReservation(reservationsRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int deleteReservation(@PathVariable Long id) {
        return roomescapeService.removeReservation(id);
    }
}
