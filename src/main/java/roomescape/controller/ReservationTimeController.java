package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/admin/times")
    public List<ReservationTime> readReservationTimes() {
        return reservationTimeDao.findAllReservationTimes();
    }

    @PostMapping("/admin/times")
    public void saveReservationTime(
        @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        reservationTimeDao.saveReservationTime(reservationTimeRequestDto.toReservationTime());
    }
}
