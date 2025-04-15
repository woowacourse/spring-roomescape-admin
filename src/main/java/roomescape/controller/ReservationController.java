package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations(){
        reservations.add(new Reservation(0L, "asd", LocalDate.now(),
                LocalTime.now()));
        return reservations;
    }

    @RequestMapping("/admin/reservation")
    public String reservation(Model model){
        model.addAttribute("reservations", reservations);
        return "admin/reservation-legacy";
    }
}
