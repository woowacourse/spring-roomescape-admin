package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationFindResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationFindResponse> findAllReservation() {
        return reservationService.findAll();
    }

    @PostMapping
    public ResponseEntity<ReservationFindResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
        Reservation savedReservation = reservationService.save(request);
        return ResponseEntity.ok()
                .header("Location", "/reservations/" + savedReservation.getId())
                .body(ReservationFindResponse.from(savedReservation));
    }

    @DeleteMapping("/{reservation_id}")
    public void deleteReservation(@PathVariable(value = "reservation_id") Long id) {
        reservationService.deleteById(id);
    }
}
