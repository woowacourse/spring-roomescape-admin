package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(
            @RequestBody ReservationTimeCreationRequest request
    ) {
        if (reservationTimeRepository.findByStartAt(request.getStartAt()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        ReservationTime newReservation = ReservationTime.createWithoutId(request.getStartAt());
        long id = reservationTimeRepository.add(newReservation);

        Optional<ReservationTime> addedReservation = reservationTimeRepository.findById(id);
        if (addedReservation.isEmpty()) {
            throw new IllegalArgumentException("ID에 해당하는 예약이 존재하지 않습니다.");
        }

        return ResponseEntity
                .created(URI.create("times/" + id))
                .body(addedReservation.get());
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("reservationTimeId") Long id) {
        if (reservationTimeRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!reservationRepository.findAllByReservationTimeId(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
