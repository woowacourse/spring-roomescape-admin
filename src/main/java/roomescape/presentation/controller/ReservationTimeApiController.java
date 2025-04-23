package roomescape.presentation.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.presentation.dto.ReservationTimeRequestDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public final class ReservationTimeApiController {

    private final ReservationTimeService reservationService;

    public ReservationTimeApiController(ReservationTimeService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationTimeResponseDto> reservationTimes() {
        return reservationService.getAllReservationTimes();
    }

    @PostMapping
    public ReservationTimeResponseDto create(@Valid @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        return reservationService.addReservationTime(reservationTimeRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteReservationTime(id);
    }
}
