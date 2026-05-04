package roomescape.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final ReservationTimeService reservationTimeService;

    public TimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }
    @PostMapping
    public TimeCreateResponse createTime(
            @RequestBody TimeCreateRequest request
    ) {
        return reservationTimeService.createTime(request.startAt());
    }

    @GetMapping
    public List<TimeCreateResponse> readAllTimes() {
        return reservationTimeService.readAllTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(
            @PathVariable("id") Long id
    ) {
        reservationTimeService.deleteTime(id);
    }
}
