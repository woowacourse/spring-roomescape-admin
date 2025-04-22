package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDao;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.entity.TimeEntity;

import java.util.List;

@RestController
@RequestMapping("/times")
public class RoomescapeTimeRestController {
    private final TimeDao timeDao;

    public RoomescapeTimeRestController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> create(@RequestBody TimeRequestDto requestDto) {
        TimeEntity saved = timeDao.save(requestDto.toEntity());
        return ResponseEntity.ok().body(TimeResponseDto.from(saved));
    }

    @GetMapping
    public List<TimeResponseDto> getAllTimes() {
        return timeDao.findAll().stream()
                .map(TimeResponseDto::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        timeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
