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
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> getTimes() {
        return reservationTimeService.findAll();
    }

    @PostMapping
    public ReservationTimeResponse save(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.save(reservationTimeRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        try {
            reservationTimeService.remove(id);
            return ResponseEntity.ok()
                    .build();
        } catch (final IllegalArgumentException exception) {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
