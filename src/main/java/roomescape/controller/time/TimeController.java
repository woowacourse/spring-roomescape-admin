package roomescape.controller.time;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository timeRepository;
    private final ReservationTimeMapper timeMapper;

    public TimeController(ReservationTimeRepository timeRepository, ReservationTimeMapper timeMapper) {
        this.timeRepository = timeRepository;
        this.timeMapper = timeMapper;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> responses = timeRepository.findAll().stream()
                .map(timeMapper::map)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody final TimeRequest timeRequest) {
        ReservationTime time = timeMapper.map(timeRequest);
        ReservationTime savedTime = timeRepository.save(time);
        TimeResponse response = timeMapper.map(savedTime);

        return ResponseEntity
                .status(HttpStatus.OK)
                .location(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") final Long id) {
        if (timeRepository.deleteById(id) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
