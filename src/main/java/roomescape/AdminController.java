package roomescape;

import java.net.URI;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/admin/reservations")
    public ResponseEntity<Map<Long, Reservation>> getReservations() {
        return ResponseEntity.ok(adminRepository.getReservations());
    }

    @PostMapping("/admin/reservations")
    public ResponseEntity addReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = adminRepository.saveReservation(request);
        return ResponseEntity
                .created(URI.create("/admin/reservations/" + reservation.id()))
                .build();
    }

    @DeleteMapping("/admin/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        adminRepository.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
