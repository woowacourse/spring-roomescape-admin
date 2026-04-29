package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.request.ReservationTimeRequest;
import roomescape.response.ReservationTimeResponse;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ReservationTimeResponse addReservationTime(@RequestBody ReservationTimeRequest request) {
        return reservationTimeRepository.addTime(request);
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAllReservationTimes();
        return ReservationTimeResponse.from(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.deleteTime(id);
    }
}
