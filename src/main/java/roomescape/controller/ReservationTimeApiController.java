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
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(
            @RequestBody final CreateReservationTimeRequest createReservationTimeRequest
    ) {
        return ResponseEntity.ok(reservationTimeService.createReservationTime(createReservationTimeRequest));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        return ResponseEntity.ok(reservationTimeService.findAllReservationTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") final Long id) {
        reservationTimeService.deleteReservationTimeById(id);
        return ResponseEntity.ok().build();
    }
}
