package roomescape.time;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody TimeCreateRequest timeCreateRequest) {
        return ResponseEntity.ok(new TimeResponse(1L, "10:00"));
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> responses = List.of(new TimeResponse(1L, "10:00"));
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}
