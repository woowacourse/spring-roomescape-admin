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
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        return reservationTimeRepository.createReservationTime(reservationTime);
    }

    @GetMapping
    ResponseEntity<List<ReservationTime>> read() {
        return reservationTimeRepository.readReservationTimes();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        return reservationTimeRepository.deleteReservationTime(id);
    }
}
