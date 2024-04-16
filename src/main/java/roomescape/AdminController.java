package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String enterWellComePage() {
        return "admin/index";
    }

    @GetMapping("/admin")
    public String enterAdminPage() {
        return "admin/index";
    }
}
