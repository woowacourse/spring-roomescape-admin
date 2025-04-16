package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    @GetMapping("/admin/reservation")
    public String reservation(){
        return "./admin/reservation-legacy";
    }
}
