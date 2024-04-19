package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }

    @GetMapping
    public List<ReservationResponseDto> findAll() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ReservationResponseDto create(@RequestBody ReservationRequestDto reservationRequestDto) {
        return reservationService.addReservation(reservationRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }
}
