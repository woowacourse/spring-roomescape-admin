package roomescape.time.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.utils.TimeMapper;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final List<Time> times = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Autowired
    private TimeMapper timeMapper;

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody @Valid TimeRequest timeRequest) {
        Time time = timeMapper.toTime(timeRequest);
        Time savedTime = new Time(index.getAndIncrement(), time);
        times.add(savedTime);
        TimeResponse timeResponse = timeMapper.toTimeResponse(savedTime);
        return ResponseEntity.ok(timeResponse);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> timeResponses = times.stream()
                .map(timeMapper::toTimeResponse)
                .toList();
        return ResponseEntity.ok(timeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        try {
            Time targetTime = times.stream()
                    .filter(time -> Objects.equals(time.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("삭제할 ID 없음"));
            times.remove(targetTime);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
