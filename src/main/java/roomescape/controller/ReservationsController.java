package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final ReservationDao reservationDao;

    public ReservationsController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationDao.findAll();
    }

    @PostMapping
    public Reservation create(@RequestBody ReservationRequest reservationRequest) {
        return reservationDao.insert(reservationRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationDao.delete(id);
    }
}
