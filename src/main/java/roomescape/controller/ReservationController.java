package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.TimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.domain_entity.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TimeDao timeDao;

    @GetMapping("/admin")
    public String displayMain() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String displayAdminReservation() {
        return "/admin/reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> readReservations() {
        List<Reservation> reservationResponseDtos = reservationService.findAllReservations();
        return ResponseEntity.ok().body(reservationResponseDtos);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequestDto reservationRequest
    ) {
        Reservation newReservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteReservation(
            @PathVariable("id") long idRequest
    ) {
        try {
            reservationService.deleteReservation(idRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
