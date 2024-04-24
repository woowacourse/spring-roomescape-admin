package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.mapper.ReservationMapper;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ReservationMapper mapper;

    public ReservationApiController(ReservationService reservationService, ReservationMapper mapper) {
        this.reservationService = reservationService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAllReservations().stream()
                .map(mapper::mapReservationToResponse)
                .toList();
    }

    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationService.addReservation(reservationRequest);

        return mapper.mapReservationToResponse(newReservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservationById(id);
    }
}
