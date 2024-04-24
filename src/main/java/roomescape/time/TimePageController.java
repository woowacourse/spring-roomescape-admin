package roomescape.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimePageController {

    @GetMapping("/admin/time")
    public String timePage() {
        return "admin/time";
    }
}
