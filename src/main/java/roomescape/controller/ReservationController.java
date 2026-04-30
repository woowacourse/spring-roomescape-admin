package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간: " + request.timeId()));

        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        Reservation saved = reservationRepository.save(reservation);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/reservations")
    public List<Reservation> read() {
        return reservationRepository.findAll();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

