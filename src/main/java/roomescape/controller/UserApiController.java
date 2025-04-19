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
import roomescape.service.ReservationService;

@RestController
public class UserApiController {

    private final ReservationService reservationService;

    public UserApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public List<Reservation> readReservations() {
        return reservationService.readAll();
    }

    @PostMapping("reservations")
    public Reservation add(@RequestBody ReservationRequestDto reservationDto) {
        return reservationService.add(reservationDto);
    }

    @DeleteMapping("reservations/{reservationId}")
    public void delete(@PathVariable("reservationId") Long id) {
       reservationService.delete(id);
    }
}
