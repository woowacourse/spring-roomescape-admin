package roomescape.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;

@Controller
public class RoomEscapeController {

    private final List<Reservation> reservations = new ArrayList<>(List.of(new Reservation(1L,"브라운", LocalDateTime.of(2024,4,1,10,0)),
            new Reservation(2L,"솔라",LocalDateTime.of(2024,4,1,11,0)),
            new Reservation(3L,"브리",LocalDateTime.of(2024,4,2,14,0))));



    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationAdminPage(){
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationDto> reservationsJson(){
        return reservations.stream().map(ReservationDto::EntityToDto).toList();
    }

}
