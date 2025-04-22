package roomescape.presentation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.business.service.ReservationTimeService;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(final ReservationTimeService service) {
        this.service = service;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> add(@RequestBody ReservationTimeCreateRequest request) {
        final ReservationTimeResponse response = service.saveAndGet(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        final List<ReservationTimeResponse> response = service.getAll();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationTimeId") long id) {
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
