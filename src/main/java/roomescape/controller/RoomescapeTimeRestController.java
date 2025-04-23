package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.service.RoomescapeTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class RoomescapeTimeRestController {
    private final RoomescapeTimeService service;

    public RoomescapeTimeRestController(RoomescapeTimeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> create(@RequestBody TimeRequestDto requestDto) {
        TimeResponseDto responseDto = service.create(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping
    public List<TimeResponseDto> getAllTimes() {
        return service.getAllTimes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
