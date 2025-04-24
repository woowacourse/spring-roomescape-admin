package roomescape.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.presentation.dto.TimeRequest;
import roomescape.presentation.dto.TimeResponse;
import roomescape.business.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> create(
            @RequestBody final TimeRequest timeRequest
    ) {
        final TimeResponse timeResponse = timeService.create(timeRequest);

        return ResponseEntity.ok(timeResponse);
    }
}
