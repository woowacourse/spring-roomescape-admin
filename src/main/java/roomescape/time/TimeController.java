package roomescape.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(
            @Autowired TimeService timeService
    ) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody final TimeRequest request
    ) {
        final TimeResponse response = timeService.createTime(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAllTime() {
        final List<TimeResponse> response = timeService.findAllTime();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(
            @PathVariable("id") final Long id
    ) {
        try {
            timeService.deleteTimeById(id);
            return ResponseEntity.ok().build();
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
