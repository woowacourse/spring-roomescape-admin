package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTimeCreateRequest request) {
        Long id = reservationTimeRepository.add(request);
        return ResponseEntity.ok(new ReservationTime(id, request.startAt()));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getTimes() {
        return ResponseEntity.ok(reservationTimeRepository.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.delete(id);
    }
}
