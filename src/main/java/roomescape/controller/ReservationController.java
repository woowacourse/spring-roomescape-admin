package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String getMainPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readReservation() {
        return reservations;
    }
}
