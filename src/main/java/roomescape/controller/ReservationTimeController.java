package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.repository.ReservationTimeRepository;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        var response = reservationTimeRepository.getAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> times(@RequestBody @Valid ReservationTimeRequest request) {
        var saved = reservationTimeRepository.save(new ReservationTime(request.id(), request.startAt()));
        return ResponseEntity.ok(ReservationTimeResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> times(@PathVariable Long id) {
        reservationTimeRepository.remove(id);
        return ResponseEntity.ok().build();
    }
}
