package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.service.RoomEscapeService;

@RestController
public class RoomEscapeCommandController {

    private final RoomEscapeService roomEscapeService;

    public RoomEscapeCommandController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @GetMapping("reservations")
    public List<Reservation> readReservations() {
        return roomEscapeService.readAll();
    }

    @PostMapping("reservations")
    public Reservation add(@RequestBody ReservationRequestDto reservationDto) {
        return roomEscapeService.add(reservationDto);
    }

    @DeleteMapping("reservations/{reservationId}")
    public void delete(@PathVariable("reservationId") Long id) {
       roomEscapeService.delete(id);
    }
}
