package roomescape.presentation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.business.Reservation;
import roomescape.business.ReservationTime;
import roomescape.business.service.ReservationService;

@Controller
public class ReservationQueryController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationQueryController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public String home() {
        return "home/index";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping("admin/time")
    public String time() {
        return "admin/time";
    }

    @GetMapping("reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> readReservations() {
        List<Reservation> reservations = reservationService.readReservationAll();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("reservations/{reservationId}")
    @ResponseBody
    public ResponseEntity<Reservation> readReservation(@PathVariable("reservationId") Long id) {
        Reservation reservation = reservationService.readReservationOne(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("times")
    @ResponseBody
    public ResponseEntity<List<ReservationTime>> readReservationTimes() {
        List<ReservationTime> reservationTimes = reservationService.readTimeAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @GetMapping("times/{timeId}")
    @ResponseBody
    public ResponseEntity<ReservationTime> readReservationTime(@PathVariable("timeId") Long id) {
        ReservationTime reservationTime = reservationService.readTimeOne(id);
        return ResponseEntity.ok(reservationTime);
    }
}
