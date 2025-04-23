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
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {
        var response = reservationRepository.getAll().stream()
            .map(ReservationResponse::from)
            .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody @Valid ReservationRequest request) {
        var reservationTime = reservationTimeRepository.findById(request.timeId())
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 예약 시간입니다."));
        var saved = reservationRepository.save(new Reservation(request.id(), request.name(),
            request.date(), reservationTime));
        return ResponseEntity.ok(ReservationResponse.from(saved));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationRepository.remove(reservationId);
        return ResponseEntity.ok().build();
    }
}
