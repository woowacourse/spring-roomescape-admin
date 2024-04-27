package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeManagementDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final ReservationDao reservationDao;
    private final TimeManagementDao timeManagementDao;

    public ReservationsController(ReservationDao reservationDao, TimeManagementDao timeManagementDao) {
        this.reservationDao = reservationDao;
        this.timeManagementDao = timeManagementDao;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationDao.findAll();
    }

    @PostMapping
    public Reservation create(@RequestBody ReservationRequest reservationRequest) {
        long timeId = reservationRequest.timeId();
        Time time = timeManagementDao.findById(timeId);
        return reservationDao.insert(reservationRequest, time);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationDao.delete(id);
    }
}
