package roomescape.domain.time.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.time.dto.request.TimeCreateRequestDTO;
import roomescape.domain.time.dto.response.TimeResponseDTO;
import roomescape.domain.time.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<TimeResponseDTO> getTimes() {
        return timeService.getTimes();
    }

    @PostMapping
    public TimeResponseDTO saveTime(@RequestBody TimeCreateRequestDTO requestDTO) {
        return timeService.saveTime(requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable Long id) {
        timeService.deleteTimeById(id);
    }
}
