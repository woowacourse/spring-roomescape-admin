package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationService.getReservations();
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody ReservationRequest request) {
        return reservationService.saveReservation(request);
    }

    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable Long id, HttpServletResponse response) {
        boolean isDeleted = reservationService.deleteReservation(id);

        if (!isDeleted) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
