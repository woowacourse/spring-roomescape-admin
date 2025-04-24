package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dao.ReservationTimeDAO;
import roomescape.reservation.dto.ReservationTimeReqDTO;
import roomescape.reservation.model.ReservationTime;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeDAO timeDAO;

    public ReservationTimeController(ReservationTimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeReqDTO timeDto) {
        ReservationTime reservationTimeInfo = timeDto.toEntity();
        ReservationTime newReservationTime = timeDAO.insert(reservationTimeInfo);
        return ResponseEntity.ok(newReservationTime);
    }
}
