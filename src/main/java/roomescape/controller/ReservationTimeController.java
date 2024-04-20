package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationTimeSaveDto;

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
        final List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveTime(@RequestBody final ReservationTimeSaveRequest reservationTimeSaveRequest) {
        final ReservationTimeSaveDto reservationTimeSaveDto = ReservationTimeSaveDto.from(reservationTimeSaveRequest);
        final ReservationTime savedReservationTime = reservationTimeDao.save(reservationTimeSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservationTimeResponse.from(savedReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(final @PathVariable("id") Long id) {
        boolean isDeleted = reservationTimeDao.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
