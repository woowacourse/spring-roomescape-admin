package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private List<ReservationTime> reservationTimes = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readAllReservation() {
        return ResponseEntity.ok(reservationService.readAllReservation());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.toEntity(index.getAndIncrement());
        reservationTimes.add(reservationTime);
        return ResponseEntity.ok(ReservationTimeResponse.fromEntity(reservationTime));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> readAllReservationTime() {
        return ResponseEntity.ok(ReservationTimeResponse.fromEntities(reservationTimes));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        ReservationTime reservationTime = reservationTimes.stream()
                .filter(it -> Objects.equals(it.id(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservationTimes.remove(reservationTime);
        return ResponseEntity.ok().build();
    }
}
