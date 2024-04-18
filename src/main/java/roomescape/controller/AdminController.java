package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.service.AdminService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final AtomicLong index = new AtomicLong(1);

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
    public List<Reservation> reservations() {
        return adminService.getAllReservations();
    }

    @PostMapping("reservations")
    @ResponseBody
    public Reservation reserve(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationDto);
        adminService.addReservation(reservation);
        return adminService.findReservation(reservation.getId());
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        adminService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
