package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequestDto;
import roomescape.controller.dto.ReservationTimeResponseDto;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponseDto create(@RequestBody ReservationTimeCreateRequestDto dto) {
        ReservationTime found = reservationTimeService.save(dto);

        return ReservationTimeResponseDto.toDto(found);
    }

    @GetMapping
    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeService.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::toDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationTimeService.deleteById(id);
    }
}
