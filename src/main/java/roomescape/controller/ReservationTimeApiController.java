package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> postReservationTime(@RequestBody @Validated ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeService.createReservationTime(requestDto.toEntity());
        URI location = UriComponentsBuilder.newInstance()
                .path("/times/{id}")
                .buildAndExpand(reservationTime.getId())
                .toUri();

        return ResponseEntity.created(location).body(ReservationTimeResponseDto.from(reservationTime));
    }

    @GetMapping
    public List<ReservationTimeResponseDto> getReservationTimes() {
        return reservationTimeService.findReservationTimes().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.removeReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
