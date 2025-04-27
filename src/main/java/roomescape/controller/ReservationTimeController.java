package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> readAll() {
        return reservationTimeService.readAll();
    }

    @PostMapping
    public ReservationTimeResponse create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.create(reservationTimeRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable(name = "id") long id) {
        reservationTimeService.deleteById(id);
    }
}
