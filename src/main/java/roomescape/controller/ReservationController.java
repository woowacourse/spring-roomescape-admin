package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.storage.ReservationStorage;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationStorage reservationStorage;

    public ReservationController(ReservationStorage reservationStorage) {
        this.reservationStorage = reservationStorage;
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationStorage.save(reservationRequest);
        return toResponse(reservation);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationStorage.findAllReservations()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{reservationId}")
    public void delete(@PathVariable long reservationId) {
        reservationStorage.delete(reservationId);
    }
}
