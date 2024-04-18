package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequestDto;
import roomescape.service.AdminService;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController() {
        this.adminService = new AdminService();
    }

    @GetMapping("/admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return adminService.getAllReservations();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponseDto reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        return adminService.addReservation(reservationRequestDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id){
        adminService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
