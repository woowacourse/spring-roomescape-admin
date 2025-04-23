package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationController {

    private final ReservationService service;
    private final ReservationTimeService timeService;

    public ReservationController(ReservationService service, ReservationTimeService timeService) {
        this.service = service;
        this.timeService = timeService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> readReservation() {
        return service.readReservation();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto postReservation(@RequestBody ReservationRequestDto requestDto) {
        timeService.existsTimeById(requestDto.timeId());
        return service.postReservation(requestDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
