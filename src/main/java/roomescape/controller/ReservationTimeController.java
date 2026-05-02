package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.util.ReservationTimeMapper;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        return ResponseEntity.ok(convertToTimeResponses(reservationTimeService.allTimes()));
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        long timeId = reservationTimeService.saveTime(reservationTimeRequest);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeMapper.toResponse(
                reservationTimeService.findTime(timeId));
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTime(@PathVariable long id) {
        reservationTimeService.removeTime(id);
        return ResponseEntity.ok().build();
    }

    private List<ReservationTimeResponse> convertToTimeResponses(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeMapper::toResponse)
                .toList();
    }
}
