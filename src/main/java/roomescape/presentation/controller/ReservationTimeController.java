package roomescape.presentation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.presentation.dto.CreateReservationTimeDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(
            @Qualifier("webReservationTimeService") ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponseDto createTime(@RequestBody CreateReservationTimeDto request) {
        return reservationTimeService.createReservationTime(request);
    }

    @GetMapping
    public List<ReservationTimeResponseDto> getTimes() {
        return reservationTimeService.getAllReservationTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
