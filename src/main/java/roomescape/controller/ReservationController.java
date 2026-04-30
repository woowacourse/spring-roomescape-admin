package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDetailDto;
import roomescape.dto.ReservationSaveDto;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDetailDto>> getReservations() {
        List<ReservationDetailDto> responseData = reservationDao.selectAll()
                .stream()
                .map(ReservationDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDetailDto> createReservation(@RequestBody ReservationSaveDto dto) {
        ReservationTime reservationTime = readReservationTime(dto.timeId());
        Reservation savedReservation = reservationDao.insert(new Reservation(dto.name(), dto.date(), reservationTime));
        ReservationDetailDto responseData = ReservationDetailDto.from(savedReservation);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }

    private ReservationTime readReservationTime(Long id) {
        return reservationTimeDao.select(id)
                .orElseThrow(IllegalArgumentException::new);
    }

}
