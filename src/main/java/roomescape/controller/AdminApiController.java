package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
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
    public List<ReservationDto> getReservations() {
        List<ReservationDto> reservations = reservationRepository.findAll()
                .stream()
                .map(ReservationDto::from)
                .toList();

        return reservations;
    }

    @PostMapping("/reservations")
    public ReservationDto saveReservation(@RequestBody final SaveReservationRequest request) {
        Reservation reservation = request.toReservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationDto.from(savedReservation);
    }

    @DeleteMapping("/reservations/{reservation-id}")
    public void deleteReservation(@PathVariable("reservation-id") final Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
