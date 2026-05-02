package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequestDto;
import roomescape.controller.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeCreateDto;
import roomescape.service.dto.ReservationTimeDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponseDto create(@RequestBody ReservationTimeCreateRequestDto dto) {
        ReservationTimeCreateDto serviceDto = ReservationTimeCreateDto.toDto(dto);

        ReservationTimeDto reservationTime = reservationTimeService.save(serviceDto);

        return ReservationTimeResponseDto.toDto(reservationTime);
    }

    @GetMapping
    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTimeDto> found = reservationTimeService.findAll();

        return found.stream()
                .map(ReservationTimeResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationTimeService.deleteById(id);
    }
}
