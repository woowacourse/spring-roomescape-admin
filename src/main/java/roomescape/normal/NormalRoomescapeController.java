package roomescape.normal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NormalRoomescapeController {

    @GetMapping("/")
    public String getDefaultPage() {
        return "main/index";
    }
}
