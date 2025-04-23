package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(
            @RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.createReservationTime(reservationTimeRequest.startAt());
        return new ReservationTimeResponse(reservationTime);
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeDao.getReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable("id") final Long id) {
        reservationTimeDao.deleteReservationTimeById(id);
    }
}
