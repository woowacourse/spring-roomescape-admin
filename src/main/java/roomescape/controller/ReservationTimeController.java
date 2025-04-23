package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        ReservationTimes reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.getReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime newReservationTime = request.toReservationTime(null);
        ReservationTime savedReservationTime = reservationTimeDao.save(newReservationTime);

        return new ReservationTimeResponse(savedReservationTime);
    }
}
