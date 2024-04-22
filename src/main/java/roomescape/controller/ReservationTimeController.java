package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final AtomicLong id = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @ResponseBody
    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(id.getAndIncrement(), reservationTimeRequest.time());
        reservationTimes.add(reservationTime);
        return new ReservationTimeResponse(reservationTime);
    }

    @ResponseBody
    @GetMapping
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTimeById(@PathVariable long id) {
        boolean isExist = reservationTimes.stream().anyMatch(reservationTime -> reservationTime.getId() == id);
        if (isExist) {
            reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
