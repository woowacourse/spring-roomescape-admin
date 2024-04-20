package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        final List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.getTimes();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveTime(@RequestBody final ReservationTimeSaveRequest reservationTimeSaveRequest) {
        final ReservationTimeResponse reservationTimeResponse = reservationTimeService.saveTime(reservationTimeSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(final @PathVariable("id") Long id) {
        try {
            reservationTimeService.deleteTime(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("예약 시간 삭제 실패", e);
            return ResponseEntity.notFound().build();
        }
    }
}
