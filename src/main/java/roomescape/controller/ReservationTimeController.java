package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

        return new ResponseEntity<>(reservationTimeResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody final ReservationTimeRequestDto reservationTimeRequestDto) {
        final ReservationTimeResponseDto reservationTimeResponseDto =
            reservationTimeService.createReservationTime(reservationTimeRequestDto.startAt());

        return new ResponseEntity<>(reservationTimeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservationTimeService.removeReservationTime(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
