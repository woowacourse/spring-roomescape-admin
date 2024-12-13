package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

@RequiredArgsConstructor
@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ReservationTimeResponse createTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeService.create(reservationTimeRequest);
    }

    @GetMapping
    public List<ReservationTimeResponse> findAllTimes() {
        return reservationTimeService.findAll();
    }

    @DeleteMapping("/{id}")
    public void cancelTime(@PathVariable Long id) {
        reservationTimeService.remove(id);
    }
}
