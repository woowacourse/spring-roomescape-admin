package roomescape.controller.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminTimeController {

    @GetMapping("/time")
    public String getAdminTimePage() {
        return "admin/time";
    }
}
