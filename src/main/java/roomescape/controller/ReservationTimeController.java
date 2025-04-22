package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;

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

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeDao.insert(reservationTimeRequest.toEntity());
        return ResponseEntity.ok().body(ReservationTimeResponse.toDto(newReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {
        if (reservationTimeDao.deleteById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
