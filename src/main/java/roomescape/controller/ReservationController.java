package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Long savedId = reservationService.addReservation(reservationRequest);
        String redirectUrl = "/reservations/" + savedId;
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", redirectUrl).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getAllReservations();
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.getReservation(id);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
