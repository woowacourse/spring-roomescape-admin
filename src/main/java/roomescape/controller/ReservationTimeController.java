package roomescape.controller;

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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.idgenerator.AutoIncrementIdGenerator;
import roomescape.idgenerator.IdGenerator;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;
    private final IdGenerator idGenerator = new AutoIncrementIdGenerator();

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAllTimes() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return ResponseEntity.ok(reservationTimes.stream()
                .map(ReservationTimeResponse::new)
                .toList());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addTime(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toDomain(
                idGenerator.generateNewId());
        reservationTimeDao.save(reservationTime);
        return ResponseEntity.ok(new ReservationTimeResponse(reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        boolean isDeleted = reservationTimeDao.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
