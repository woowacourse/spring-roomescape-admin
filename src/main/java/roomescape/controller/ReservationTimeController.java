package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeCreateRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationTimeDao;

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
        try {
            Long id = reservationTimeDao.saveAndReturnId(requestDto.startAt());
            ReservationTimeResponseDto responseDto = new ReservationTimeResponseDto(id, requestDto.startAt());

            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
