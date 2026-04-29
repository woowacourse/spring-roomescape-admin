package roomescape.reservationtime.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.service.ReservationTimeService;
import roomescape.reservationtime.web.dto.ReservationTimeCreateDto;
import roomescape.reservationtime.web.dto.ReservationTimeDto;

@RequiredArgsConstructor
@RequestMapping("/times")
@RestController()
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @GetMapping
    public ResponseEntity<List<ReservationTimeDto>> findAllReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.findAllReservationTimes());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(
            @RequestBody ReservationTimeCreateDto request
    ) {
        return ResponseEntity.ok(reservationTimeService.saveReservationTime(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
