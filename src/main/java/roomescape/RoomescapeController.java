package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDto> reservations() {
        return List.of(
                ReservationDto.from(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0))),
                ReservationDto.from(new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))),
                ReservationDto.from(new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)))
        );
    }
}
