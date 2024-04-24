package roomescape.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.TimeService;
import roomescape.web.dto.TimeFindAllResponse;

@RequestMapping("/times")
@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeFindAllResponse>> findAllTime() {
        List<TimeFindAllResponse> response = timeService.findAllTime();
        return ResponseEntity.ok().body(response);
    }
}
