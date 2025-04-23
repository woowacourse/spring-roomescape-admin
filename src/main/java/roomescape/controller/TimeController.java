package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequestDto;
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

    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto> addReservation(@RequestBody TimeRequestDto timeRequestDto) {
        TimeResponseDto timeResponseDto = timeService.saveTime(timeRequestDto);
        return ResponseEntity.ok(timeResponseDto);
    }

}
