package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    @ResponseBody
    public List<Reservation> findAll() {
        return reservations;
    }

}
