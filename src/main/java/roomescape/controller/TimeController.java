package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {

    // /admin/time URL을 처리하는 메서드
    @GetMapping("/admin/time")
    public String showTimePage() {
        // time.html 파일을 반환 (templates/admin/time.html이 있어야 함)
        return "admin/time";
    }
}
