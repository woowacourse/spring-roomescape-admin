package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.H2ReservationTimeRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final H2ReservationTimeRepository repository;

    @Autowired
    public ReservationTimeController(H2ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ReservationTimeResponse> findAllTime() {
        return repository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = repository.save(reservationTimeRequest.toReservationTime());
        return ResponseEntity.ok()
                .location(URI.create("/times/" + reservationTime.getId()))
                .body(ReservationTimeResponse.from(reservationTime));
    }
}
