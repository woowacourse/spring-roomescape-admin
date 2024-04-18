package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        List<ReservationEntity> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @ResponseBody
    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());
        ReservationEntity reservationEntity = reservationDao.save(reservation);
        return new ReservationResponse(reservationEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        if (reservationDao.existsById(id)) {
            reservationDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
