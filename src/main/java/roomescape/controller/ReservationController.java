package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation createdReservation = reservationRepository.createReservation(reservationRequest.toReservation());
        ReservationTime reservationTime = reservationTimeRepository.readReservationTime(createdReservation.getTimeId());

        return ResponseEntity.ok(ReservationResponse.of(createdReservation, reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read() {
        List<Reservation> reservations = reservationRepository.readReservations();
        List<ReservationResponse> reservationResponses = reservations.stream().map(reservation -> {
            ReservationTime reservationTime = reservationTimeRepository.readReservationTime(reservation.getTimeId());
            return ReservationResponse.of(reservation, reservationTime);
        }).toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationRepository.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
