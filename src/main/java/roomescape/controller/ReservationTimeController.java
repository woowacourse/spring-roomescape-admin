package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.JdbcReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDetailDto;
import roomescape.dto.ReservationTimeSaveDto;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final JdbcReservationTimeDao reservationTimeDao;

    public ReservationTimeController(JdbcReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeDetailDto>> getReservationTimes() {
        List<ReservationTimeDetailDto> responseData = reservationTimeDao.selectAll().stream()
                .map(ReservationTimeDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDetailDto> createReservationTime(@RequestBody ReservationTimeSaveDto dto) {
        ReservationTime savedReservationTime = reservationTimeDao.insert(new ReservationTime(dto.startAt()));
        ReservationTimeDetailDto responseData = ReservationTimeDetailDto.from(savedReservationTime);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.ok().build();
    }

}
