package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

import java.util.List;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTimes() {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos =
            reservationTimeService.getAllReservationTimes();

        return ResponseEntity.ok(reservationTimeResponseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody final ReservationTimeRequestDto reservationTimeRequestDto) {
        final ReservationTimeResponseDto reservationTimeResponseDto =
            reservationTimeService.createReservationTime(reservationTimeRequestDto.startAt());

        return ResponseEntity.ok(reservationTimeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservationTimeService.removeReservationTime(id);

        return ResponseEntity.ok().build();
    }
}
