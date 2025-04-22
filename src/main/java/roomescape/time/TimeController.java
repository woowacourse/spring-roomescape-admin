package roomescape.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody final TimeRequest request
    ) {
        final TimeResponse response = timeService.createTime(request);
        return ResponseEntity.ok(response);
    }


}
