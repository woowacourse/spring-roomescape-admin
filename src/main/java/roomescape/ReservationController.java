package roomescape;

import org.springframework.ui.Model;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation(Model model) {
        reservations.add(new Reservation(1, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2, "브라운", "2023-01-02", "11:00"));
        model.addAttribute("list", reservations);
        return "admin/reservation-legacy";
    }
}
