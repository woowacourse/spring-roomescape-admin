package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationController {

    @Autowired
    private ReservationDao reservationDao;

    @GetMapping("/reservations")
    public List<ReservationResponse> readAllReservations() {
        return reservationDao.getReservations();
    }

//    @PostMapping("/reservations")
//    public Reservation createReservation(@RequestBody ReservationRequest request) {
//        Reservation newReservation = new Reservation(index.getAndIncrement(), request.name(), request.date(), request.time());
//        reservations.add(newReservation);
//        return newReservation;
//    }
//
//    @DeleteMapping("/reservations/{id}")
//    public void deleteReservation(@PathVariable long id) {
//        Reservation target = reservations.stream()
//                .filter(reservation -> reservation.getId() == id)
//                .findAny()
//                .orElseThrow(RuntimeException::new);
//
//        reservations.remove(target);
//    }
}
