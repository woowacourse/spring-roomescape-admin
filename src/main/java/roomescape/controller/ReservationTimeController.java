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
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ReservationTimeResponseDto create(@RequestBody final ReservationTimeRequestDto request) {
        return reservationTimeService.create(request);
    }

    @GetMapping("/times")
    public List<ReservationTimeResponseDto> find() {
        return reservationTimeService.find();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
