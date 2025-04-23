package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.model.ReservationDao;
import roomescape.model.Time;
import roomescape.model.TimeDao;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final TimeDao timeDao;
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationController(TimeDao timeDao, ReservationDao reservationDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<ReservationResponse> getReservationDao() {
        return reservationDao.getReservations();
    }

    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        Time findTime = timeDao.findById(request.timeId());
        return reservationDao.createReservation(request.toEntity(findTime));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationDao.deleteReservation(id);
    }
}
