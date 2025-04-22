package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeResponse;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
        return ResponseEntity.ok().body(reservationTimes);
    }
}
