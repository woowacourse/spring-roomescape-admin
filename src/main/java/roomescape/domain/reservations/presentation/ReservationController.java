package roomescape.domain.reservations.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationJdbcTemplateRepository;
import roomescape.domain.reservations.infrastructure.ReservationTimeJdbcTemplateRepository;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

@RestController
public class ReservationController {

    private final ReservationJdbcTemplateRepository reservationRepository;
    private final ReservationTimeJdbcTemplateRepository reservationTimeRepository;

    public ReservationController(
            ReservationJdbcTemplateRepository reservationRepository,
            ReservationTimeJdbcTemplateRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(
            @RequestBody ReservationRequest request
    ) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(IllegalArgumentException::new);

        Reservation reservation = Reservation.of(
                null,
                request.name(),
                request.date(),
                time
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(ReservationResponse.from(savedReservation, time));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations).getBody();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> addTime(
            @RequestBody ReservationTimeRequest request
    ) {
        ReservationTime reservationTime = ReservationTime.of(
                null,
                request.startAt()
        );
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(ReservationTimeResponse.from(savedReservationTime));
    }

    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return ResponseEntity.ok(times).getBody();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long id
    ) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
