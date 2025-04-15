package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.service.RoomescapeService;

@Controller
public class RoomescapeController {

    private final RoomescapeService roomescapeService;

    public RoomescapeController(final RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservationList() {
        List<Reservation> reservations = roomescapeService.findReservations();
        return ResponseEntity.ok(reservations);
    }
}
