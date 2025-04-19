package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.dto.ReservationReqDto;
import roomescape.domain.dto.ReservationResDto;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
public class UserApiController {

    private final ReservationService reservationService;

    public UserApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public List<ReservationResDto> readReservations() {
        return reservationService.readAll();
    }

    @PostMapping("reservations")
    public ReservationResDto add(@RequestBody ReservationReqDto dto) {
        return reservationService.add(dto);
    }

    @DeleteMapping("reservations/{reservationId}")
    public void delete(@PathVariable("reservationId") Long id) {
       reservationService.delete(id);
    }
}
