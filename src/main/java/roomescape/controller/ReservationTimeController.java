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
import roomescape.repository.ReservationTimes;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimes reservationTimes;

    public ReservationTimeController(ReservationTimes reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    @GetMapping
    public List<ReservationTimeFindResponse> getReservationTimes() {
        return reservationTimes.findReservationTimes().stream()
                .map(ReservationTimeFindResponse::of)
                .toList();
    }

    @PostMapping
    public ReservationTimeCreateResponse createReservationTime(
            @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {

        ReservationTime createdReservationTime = reservationTimes.createReservationTime(
                reservationTimeCreateRequest);

        return ReservationTimeCreateResponse.of(createdReservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimes.deleteReservationTimeById(id);
    }
}
