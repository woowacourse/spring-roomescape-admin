package roomescape.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservationTime(
            @RequestBody final ReservationTimeCreateRequest request
    ) {
        ReservationTime reservationTime = reservationTimeDao.insert(
                new ReservationTime(0L, request.startAt()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReservationTimeResponse(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> reservationTimes() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .toList();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") final Long id) {
        reservationTimeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
