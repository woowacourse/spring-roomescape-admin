package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationDetailDto;
import roomescape.dto.ReservationSaveDto;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
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
        Reservation savedReservation = reservationDao.insert(dto.name(), dto.date(), dto.time());
        ReservationDetailDto responseData = ReservationDetailDto.from(savedReservation);
        return ResponseEntity.ok(responseData);
    }

}
