package roomescape.web.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.general.domain.ReservationTime;
import roomescape.general.dto.ReservationTimeRequest;
import roomescape.general.dto.ReservationTimeResponse;
import roomescape.general.service.ReservationTimeService;


@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(
            @RequestBody final ReservationTimeRequest reservationTimeRequest
    ) {
        final ReservationTime reservationTime = reservationTimeService.create(reservationTimeRequest);
        return ResponseEntity.created(URI.create("/times/" + reservationTime.getId()))
                .body(ReservationTimeResponse.from(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        return ResponseEntity.ok()
                .body(reservationTimeService.findAll().stream()
                        .map(ReservationTimeResponse::from)
                        .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable final long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
