package roomescape.reservation.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.time.dto.ReservationTimeRequestDto;
import roomescape.reservation.time.dto.ReservationTimeResponseDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getTimes() {
        return ResponseEntity.ok(ReservationTimeResponseDto.from(reservationTimeService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> createTime(@RequestBody ReservationTimeRequestDto request) {
        return ResponseEntity.created(URI.create("/times"))
                .body(ReservationTimeResponseDto.from(reservationTimeService.save(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
