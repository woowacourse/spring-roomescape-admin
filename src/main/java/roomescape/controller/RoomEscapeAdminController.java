package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomEscapeAdminController {

    @GetMapping("/admin")
    public String dashboard() {
        return "admin/index";
    }
}
