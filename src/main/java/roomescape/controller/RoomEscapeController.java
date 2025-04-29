package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationService;

@RestController
public class RoomEscapeController {

    private final ReservationService reservationService;

    public RoomEscapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findReservations() {
        return reservationService.findAllReservations();
    }

    @PostMapping("/reservations")
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/times")
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        return reservationService.createReservationTime(request);
    }

    @GetMapping("/times")
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationService.findAllReservationTimes();
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable("id") Long id) {
        reservationService.deleteReservationTime(id);
    }

}
