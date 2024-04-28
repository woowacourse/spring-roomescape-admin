package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService service;

    @Autowired
    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationTimeResponse> findAllTime() {
        return service.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = service.save(reservationTimeRequest);
        return ResponseEntity.ok()
                .location(URI.create("/times/" + reservationTime.getId()))
                .body(ReservationTimeResponse.from(reservationTime));
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        service.deleteById(id);
    }
}
