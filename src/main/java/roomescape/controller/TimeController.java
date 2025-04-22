package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.TimeRepository;

@Controller
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("admin/time")
    public String adminTime() {
        return "admin/time";
    }

}
