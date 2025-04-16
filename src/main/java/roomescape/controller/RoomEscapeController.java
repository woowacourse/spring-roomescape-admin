package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.Reservation;

@Controller
public class RoomEscapeController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readReservations() {
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(11, 10, 10));
        reservations.add(reservation);
        return reservations;
    }
}
