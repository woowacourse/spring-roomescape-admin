package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationController(ReservationService reservationService, final ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationService.findAll();
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @ResponseBody
    @PostMapping
    public ReservationResponse createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeService.findById(createReservationRequest.timeId());
        Reservation reservation = new Reservation(createReservationRequest.name(), createReservationRequest.date(), reservationTime);
        Reservation newReservation = reservationService.create(reservation);
        return new ReservationResponse(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
