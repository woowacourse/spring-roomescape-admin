package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/times")
    public List<ReservationTime> readReservationTimes() {
        return reservationTimeDao.findAllReservationTimes();
    }

    @PostMapping("/times")
    public void saveReservationTime(
        @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        reservationTimeDao.saveReservationTime(reservationTimeRequestDto.toReservationTime());
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable(name = "id") Long id) {
        reservationTimeDao.deleteReservationTime(id);
    }
}
