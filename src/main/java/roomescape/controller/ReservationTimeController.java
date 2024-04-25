package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    @Autowired
    ReservationTimeService reservationTimeService;

    @PostMapping()
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.create(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId())).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
