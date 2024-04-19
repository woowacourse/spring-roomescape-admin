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
import roomescape.TimeDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Controller
public class ReservationTimeController {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @GetMapping("/times")
    public ResponseEntity<List<TimeDto>> times() {
        List<ReservationTime> times = reservationTimeRepository.findAllReservationTimes();
        List<TimeDto> responseBody = times.stream()
                .map(TimeDto::from)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@RequestBody TimeDto timeDto) {
        long id = reservationTimeRepository.save(timeDto.toEntity());
        TimeDto responseBody = new TimeDto(id, timeDto.getStartAt());
        return ResponseEntity
                .created(URI.create("/time/" + id))
                .body(responseBody);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        reservationTimeRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
