package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.entity.ReservationEntity;

@Controller
public class RoomEscapeController {

    private final List<ReservationEntity> reservationEntities;

    public RoomEscapeController() {
        this.reservationEntities = new ArrayList<>();
    }

    @GetMapping("/admin")
    public String getIndexPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationEntity>> getReservations() {
        return new ResponseEntity<>(reservationEntities, HttpStatus.OK);
    }
}
