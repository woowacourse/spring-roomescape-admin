package roomescape.reservation.presentation;

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
import roomescape.reservation.presentation.dto.Reservation;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationEntity;
import roomescape.reservation.repository.ReservationsRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationsRepository reservationsRepository;

    @Autowired
    public ReservationController(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsDao() {
        List<ReservationEntity> reservationEntities = reservationsRepository.getReservations();

        List<Reservation> reservations = reservationEntities.stream()
                .map(Reservation::from)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequest request
    ) {
        ReservationEntity entity = request.to();
        ReservationEntity entityWithId =
                reservationsRepository.saveReservation(entity);

        Reservation reservation = Reservation.from(entityWithId);

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationsRepository.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
