package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationDao.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationDto reservationDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationDto.timeId());
        Long id = reservationDao.save(reservationDto);
        return ResponseEntity.ok(new Reservation.Builder()
                .id(id)
                .name(reservationDto.name())
                .date(reservationDto.date())
                .time(reservationTime)
                .build());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
