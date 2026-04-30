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
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public List<ReservationTime> list() {
        return reservationTimeService.findAll();
    }

    @PostMapping("/times")
    public ReservationTime create(@RequestBody ReservationTime request) {
        return reservationTimeService.create(request);
    }

    @DeleteMapping("/times/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
    }
}
