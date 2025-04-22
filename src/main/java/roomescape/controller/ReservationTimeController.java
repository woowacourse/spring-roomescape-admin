package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Controller
@RequestMapping("times")
public class ReservationTimeController {
    private final ReservationTimeRepository timeRepository;

    public ReservationTimeController(ReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime newtime = new ReservationTime(request.getStartAt());
        ReservationTimeResponse response = ReservationTimeResponse.from(timeRepository.add(newtime));
        return ResponseEntity.created(URI.create("times/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        return ResponseEntity.ok(ReservationTimeResponse.from(timeRepository.findAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        timeRepository.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
