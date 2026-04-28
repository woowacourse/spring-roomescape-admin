package roomescape.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationSaveRequestDto;
import roomescape.service.RoomescapeService;

@RestController
public class RoomescapeController {
    private final RoomescapeService roomescapeService;

    public RoomescapeController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() { 
        return roomescapeService.getReservations();
    }

    @PostMapping("/reservations")
    public Reservation saveReservation(@RequestBody ReservationSaveRequestDto reservationRequest) {
        return roomescapeService.save(reservationRequest);
    }

    @DeleteMapping("/reservations/{id}")
    public boolean deleteReservation(@PathVariable long id) {
        return roomescapeService.deleteById(id);
    }
}