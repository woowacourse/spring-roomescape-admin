package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.db.ReservationTimeRepository;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;


@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    @Autowired
    ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTimes(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeService.createTimes(reservationTimeRequest);
        return ResponseEntity.status(201).body(ReservationTimeResponse.from(reservationTimeService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        return ResponseEntity.ok()
                .body(reservationTimeService.getTimes()
                        .stream()
                        .map(ReservationTimeResponse::from)
                        .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.status(204).header("Location", "/reservations/" + id).build();
    }
}
