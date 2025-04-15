package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationDto;

@Controller
public class RoomEscapeController {

    private final Reservations reservations = new Reservations();

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDto> readReservations() {
        return reservations.getReservations().stream()
            .map(ReservationDto::from)
            .toList();
    }
}
