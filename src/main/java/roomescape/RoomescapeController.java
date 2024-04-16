package roomescape;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

@Controller
public class RoomescapeController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = List.of(
        new Reservation(index.getAndIncrement(), "트레", "2024-04-16", "10:00"),
        new Reservation(index.getAndIncrement(), "에버", "2024-04-17", "11:00"),
        new Reservation(index.getAndIncrement(), "냥인", "2024-04-18", "12:00")
    );

    @GetMapping("/admin")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/admin/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }
}
