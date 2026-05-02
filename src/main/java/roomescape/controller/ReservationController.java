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
import roomescape.controller.dto.ReservationCreateRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationCreateDto;
import roomescape.service.dto.ReservationDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        List<ReservationDto> reservationList = reservationService.findAll();

        List<ReservationResponseDto> response = reservationList.stream()
                .map(ReservationResponseDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationCreateRequestDto dto) {
        ReservationCreateDto serviceDto = ReservationCreateDto.toDto(dto);
        
        ReservationDto saved = reservationService.save(serviceDto);

        ReservationResponseDto responseDto = ReservationResponseDto.toDto(saved);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
