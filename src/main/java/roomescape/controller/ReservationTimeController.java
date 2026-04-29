package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.JdbcReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDetailDto;
import roomescape.dto.ReservationTimeSaveDto;

@RestController
public class ReservationTimeController {

    private final JdbcReservationTimeDao reservationTimeDao;

    public ReservationTimeController(JdbcReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDetailDto> createReservationTime(@RequestBody ReservationTimeSaveDto dto) {
        ReservationTime savedReservationTime = reservationTimeDao.insert(new ReservationTime(dto.startAt()));
        ReservationTimeDetailDto responseData = ReservationTimeDetailDto.from(savedReservationTime);
        return ResponseEntity.ok(responseData);
    }

}
