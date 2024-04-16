package roomescape;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationRequest;

@Controller
public class AdminController {
    private final AdminRepository adminRepository = new AdminRepository();

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/admin/reservations")
    public ResponseEntity<Map<Long, Reservation>> getReservations() {
        return ResponseEntity.ok(adminRepository.getReservations());
    }

    @ResponseBody
    @PostMapping("/admin/reservations")
    public Reservation addReservation(@RequestBody ReservationRequest request) {
        return adminRepository.saveReservation(request);
    }

    @ResponseBody
    @DeleteMapping("/admin/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        adminRepository.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
