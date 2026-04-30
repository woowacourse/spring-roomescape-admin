package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/times")
    public List<ReservationTime> list() {
        return reservationTimeRepository.findAll();
    }

    @PostMapping("/times")
    public ReservationTime create(@RequestBody ReservationTime request) {
        return reservationTimeRepository.save(request);
    }

    @DeleteMapping("/times/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
