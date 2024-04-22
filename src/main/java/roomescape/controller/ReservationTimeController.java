package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationTimeDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeDto>> findAll() {
        List<ReservationTime> allReservationTimes = reservationTimeDao.findAll();
        List<ReservationTimeDto> results = allReservationTimes.stream()
                .map(ReservationTimeDto::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> create(@RequestBody ReservationTimeDto reservationTimeDto) {
        ReservationTime reservationTime = reservationTimeDto.toDomain();
        long id = reservationTimeDao.add(ReservationTimeDto.from(reservationTime));
        ReservationTime result = reservationTimeDao.findById(id);
        return ResponseEntity.created(URI.create("/times/" + id))
                .body(ReservationTimeDto.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
