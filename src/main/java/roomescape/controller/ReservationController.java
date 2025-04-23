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
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
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
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 예약 시간입니다."));
        return ResponseEntity.ok(reservationRepository.save(new Reservation(request.id(), request.name(),
            request.date(), reservationTime)));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationRepository.remove(reservationId);
        return ResponseEntity.ok().build();
    }
}
