package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.service.AdminService;

import java.util.Set;

// TODO 응답형태에 따른 Controller 분리
// jason -> RestController
// view
@Controller
public class AdminController {
    // TODO id 생성 Long 캡슐화
    private final AdminService adminService;

    public AdminController() {
        this.adminService = new AdminService();
    }

    @GetMapping("admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    @ResponseBody
    public Set<Reservation> reservations() {
        return adminService.getAllReservations();
    }

    @PostMapping("reservations")
    @ResponseBody
    public Reservation reserve(@RequestBody ReservationDto reservationDto) {
        return adminService.reserve(reservationDto);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        adminService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
