package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String admin(){
        return "index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation(Model model){
        model.addAttribute("reservations", reservations);
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations(){
        return ResponseEntity.ok().body(reservations);
    }
}
