package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RoomescapeController {

    @GetMapping("/admin")
    public String admin(
    ){
        return "/admin/index.html";
    }
}
