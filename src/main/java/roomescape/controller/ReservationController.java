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
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservationService.getReservations());
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) {
        Reservation createdReservation = reservationService.createReservation(
                createReservationRequest.name(),
                createReservationRequest.date(),
                createReservationRequest.timeId()
        );
        return ResponseEntity.ok().body(createdReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readTimes() {
        return ResponseEntity.ok().body(reservationService.getReservationTimes());
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> createTime(
            @RequestBody CreateReservationTimeRequest createReservationTimeRequest) {
        ReservationTime createdTime = reservationService.createReservationTime(createReservationTimeRequest.startAt());
        return ResponseEntity.ok().body(createdTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
