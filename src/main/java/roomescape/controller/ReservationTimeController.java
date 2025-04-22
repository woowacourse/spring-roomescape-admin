package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.ReservationTime;
import roomescape.repository.impl.ReservationTimeRepositoryImpl;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepositoryImpl reservationTimeRepositoryImpl;

    @Autowired
    public ReservationTimeController(ReservationTimeRepositoryImpl reservationTimeRepositoryImpl) {
        this.reservationTimeRepositoryImpl = reservationTimeRepositoryImpl;
    }

    @PostMapping
    ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime createdReservationTime = reservationTimeRepositoryImpl.createReservationTime(reservationTime);
        return ResponseEntity.ok(createdReservationTime);
    }

    @GetMapping
    ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = reservationTimeRepositoryImpl.readReservationTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeRepositoryImpl.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
