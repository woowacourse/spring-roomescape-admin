package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReservationTimeResponseDto> create(@RequestBody ReservationTimeCreateRequestDto dto) {
        ReservationTimeCreateDto serviceDto = ReservationTimeCreateDto.toDto(dto);

        ReservationTimeDto reservationTime = reservationTimeService.save(serviceDto);

        ReservationTimeResponseDto response = ReservationTimeResponseDto.toDto(reservationTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> findAll() {
        List<ReservationTimeDto> found = reservationTimeService.findAll();

        List<ReservationTimeResponseDto> response = found.stream()
                .map(ReservationTimeResponseDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
