package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.create(reservationRequest);
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.remove(id);
    }
}
