package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateResponse;
import roomescape.controller.dto.ReservationTimeFindResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeFindResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.findReservationTimes();

        return reservationTimes.stream()
                .map(ReservationTimeFindResponse::of)
                .toList();
    }

    @PostMapping
    public ReservationTimeCreateResponse createReservationTime(
            @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long createdReservationTimeId = reservationTimeService.createReservationTime(reservationTimeCreateRequest);

        ReservationTime createdReservationTime = reservationTimeService.findReservationTimeById(
                createdReservationTimeId);
        return ReservationTimeCreateResponse.of(createdReservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTimeById(id);
    }
}
