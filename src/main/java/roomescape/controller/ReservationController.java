package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.controller.dto.ReservationsResponseDto;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return ReservationsResponseDto.from(reservationService.findAll()).reservations();

    }

    @GetMapping("/{id}")
    public ReservationResponseDto getReservationById(@PathVariable long id) {
        return ReservationResponseDto.from(reservationService.findById(id));
    }

    @PostMapping
    public ReservationResponseDto saveReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        return ReservationResponseDto.from(reservationService.save(reservationRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.delete(id);
    }

}
