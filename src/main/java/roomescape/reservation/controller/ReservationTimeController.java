package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationTimeReqDTO;
import roomescape.reservation.exception.ReservationTimeNotFoundException;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.service.ReservationTimeService;

import java.net.URI;
import java.util.List;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService timeService;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService timeService, ReservationTimeService reservationTimeService) {
        this.timeService = timeService;
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeReqDTO timeDto) {
        try {
            ReservationTime created = reservationTimeService.create(timeDto);
            URI location = URI.create("/times/" + created.getId());
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getAll() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        try {
            timeService.deleteBy(id);
            return ResponseEntity.ok().build();
        } catch (ReservationTimeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
