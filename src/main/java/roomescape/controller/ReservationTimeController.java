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
import roomescape.data.dto.request.ReservationTimeRequest;
import roomescape.data.vo.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody final ReservationTimeRequest dto) {
        final var savedId = reservationTimeRepository.add(new ReservationTime(dto.getStartAt()));

        return ResponseEntity.ok(new ReservationTime(savedId, dto.getStartAt()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable final long id) {
        reservationTimeRepository.remove(id);
        return ResponseEntity.ok().build();
    }
}
