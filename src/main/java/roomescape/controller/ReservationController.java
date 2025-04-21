package roomescape.controller;

import jakarta.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
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
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final Clock clock;

    public ReservationController(
            final ReservationRepository reservationRepository,
            final ReservationTimeRepository reservationTimeRepository,
            final Clock clock
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.clock = clock;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> response = reservationRepository.getReservations()
                .stream()
                .map(ReservationDto::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody @Valid final CreateReservationDto createReservationDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationDto.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
        Reservation reservation = createReservationDto.toReservationWith(reservationTime, LocalDateTime.now(clock));
        Reservation savedReservation = reservationRepository.add(reservation);
        return ResponseEntity.ok(ReservationDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        if (!reservationRepository.existReservation(id)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
