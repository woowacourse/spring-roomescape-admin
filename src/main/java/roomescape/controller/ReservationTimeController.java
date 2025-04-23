package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @GetMapping("/times")
    public List<ReservationTimeResponseDto> readReservationTime() {
        return service.readReservationTime();
    }

    @PostMapping("/times")
    public ReservationTimeResponseDto postReservationTime(@RequestBody ReservationTimeRequestDto requestDto) {
        return service.postReservationTime(requestDto);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        service.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
