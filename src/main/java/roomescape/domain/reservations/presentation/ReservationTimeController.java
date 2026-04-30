package roomescape.domain.reservations.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.application.ReservationTimeService;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> saveTime(
            @RequestBody ReservationTimeRequest request
    ) {
        return ResponseEntity.ok(service.saveTime(request));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getTimes() {
        return ResponseEntity.ok(service.getTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long id
    ) {
        service.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
