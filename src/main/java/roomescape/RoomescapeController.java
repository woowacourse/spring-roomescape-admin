package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    @GetMapping("/")
    public String getDefaultPage() {
        return "main/index";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }
}
