package roomescape.web.presentation;

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
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.dto.ReservationTimeRequest;
import roomescape.core.service.dto.ReservationTimeResponse;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAllReservationTime() {
        List<ReservationTimeResponse> response = reservationTimeService.findAllReservationTime();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.saveReservationTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.id())).body(response);
    }

    @DeleteMapping("/{time_id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(value = "time_id") Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
