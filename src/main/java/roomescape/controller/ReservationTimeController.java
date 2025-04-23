package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeCreateRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.model.ReservationTime;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addReservationTime(@RequestBody final ReservationTimeCreateRequestDto requestDto) {
        ReservationTime reservationTime = requestDto.toEntity();
        Long id = reservationTimeDao.saveAndReturnId(reservationTime);
        ReservationTimeResponseDto responseDto = new ReservationTimeResponseDto(id, reservationTime.startAt());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getAllReservationTimes() {
        List<ReservationTimeResponseDto> allReservationTimes = reservationTimeDao.findAllReservationTimes();
        return ResponseEntity.ok(allReservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        try {
            reservationTimeDao.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
