package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> postReservationTime(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto
    ) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.startAt());
        Long id = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(new ReservationTimeResponseDto(id, reservationTime.getStartAt()));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
