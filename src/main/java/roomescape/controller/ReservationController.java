package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository = new ReservationRepository();

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationRepository.getReservations()
                .values()
                .stream()
                .toList();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationRepository.saveReservation(request);
        return new ResponseEntity(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        reservationRepository.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations")
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }
}
