package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeResponseDto;
import roomescape.service.TimeService;

@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto>>times(){
        List<TimeResponseDto> timeResponseDtos = new ArrayList<>();
        return ResponseEntity.ok(timeResponseDtos);
    }
}
