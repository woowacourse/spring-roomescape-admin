package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private List<Reservation> reservations = List.of(
            new Reservation(1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            new Reservation(2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
    );

    @GetMapping("/admin/reservation")
    public String getReservationPage(Model model) {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

}
