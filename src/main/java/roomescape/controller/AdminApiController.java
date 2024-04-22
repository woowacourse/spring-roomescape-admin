package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.SaveReservationRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;

@RestController
public class AdminApiController {
    private final ReservationRepository reservationRepository;

    public AdminApiController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponse saveReservation(@RequestBody final SaveReservationRequest request) {
        Reservation reservation = request.toReservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    @DeleteMapping("/reservations/{reservation-id}")
    public void deleteReservation(@PathVariable("reservation-id") final Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
