package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.dao.ReservationTimeDao;

@Controller
public class ReservationTimeController {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeDto>> times() {
        List<ReservationTime> times = reservationTimeDao.findAllReservationTimes();
        List<ReservationTimeDto> responseBody = times.stream()
                .map(ReservationTimeDto::from)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDto> addTime(@RequestBody ReservationTimeDto reservationTimeDto) {
        long id = reservationTimeDao.save(reservationTimeDto);
        ReservationTimeDto responseBody = new ReservationTimeDto(id, reservationTimeDto.getStartAt());
        // TODO: dto를 그대로 응답하는 것 괜찮을까?
        return ResponseEntity
                .created(URI.create("/time/" + id))
                .body(responseBody);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
