package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeRequestDto;
import roomescape.controller.dto.ReservationTimeResponseDto;
import roomescape.controller.dto.ReservationTimesResponseDto;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService timeService;

    public ReservationTimeController(ReservationTimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<ReservationTimeResponseDto> getTimes() {
        return ReservationTimesResponseDto.from(timeService.findAll()).times();
    }

    @PostMapping
    public ReservationTimeResponseDto saveTime(@RequestBody ReservationTimeRequestDto time) {
        return ReservationTimeResponseDto.from(timeService.save(time.toReservationTime()));
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable int id) {
        timeService.delete(id);
    }
}
