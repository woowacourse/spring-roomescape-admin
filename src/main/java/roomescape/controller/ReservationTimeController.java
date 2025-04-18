package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.infra.ReservationTimeDatabase;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final ReservationTimeDatabase reservationTimeDatabase;

    public ReservationTimeController(final ReservationTimeDatabase reservationTimeDatabase) {
        this.reservationTimeDatabase = reservationTimeDatabase;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> add(@RequestBody ReservationTimeCreateRequest request) {
        final long savedId = reservationTimeDatabase.saveAndGetId(request);

        final ReservationTimeResponse response = ReservationTimeResponse.from(request, savedId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        final List<ReservationTime> times = reservationTimeDatabase.findAll();

        final List<ReservationTimeResponse> response = times.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(long id) {
        reservationTimeDatabase.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
