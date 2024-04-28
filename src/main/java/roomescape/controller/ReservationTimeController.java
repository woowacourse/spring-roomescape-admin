package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTime> read() {
        return reservationTimeService.read();
    }

    @PostMapping
    public ReservationTime create(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return reservationTimeService.create(reservationTimeCreateRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        reservationTimeService.delete(id);
    }
}
