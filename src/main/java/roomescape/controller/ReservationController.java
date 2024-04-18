package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.AdminService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AdminService adminService;

    public ReservationController() {
        this.adminService = new AdminService();
    }

    @GetMapping()
    public List<Reservation> findAll() {
        return adminService.getAllReservations();
    }

    @PostMapping()
    public ReservationResponseDto create(@RequestBody ReservationRequestDto reservationRequestDto) {
        return adminService.addReservation(reservationRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        adminService.deleteReservation(id);
    }
}
