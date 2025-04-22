package roomescape.controller;

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
import roomescape.ReservationTime;
import roomescape.controller.dto.CreateTimeRequest;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<ReservationTime> saveTime(@RequestBody CreateTimeRequest createTimeRequest) {
        final ReservationTime reservationTime = reservationTimeService.saveTime(createTimeRequest.startAt());
        return ResponseEntity.ok(reservationTime);

    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        final List<ReservationTimeResponse> reservationTimeResponse = reservationTimeService.findAll();
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
