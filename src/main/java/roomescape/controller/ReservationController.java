package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> postReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시간의 id가 존재하지 않습니다."));

        Reservation reservation = new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTime
        );
        Long id = reservationRepository.save(reservation);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(
                id,
                reservation.getMemberName(),
                reservation.getDate(),
                reservation.getTime()
        );
        return ResponseEntity.ok(reservationResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> allReservation = reservationRepository.findAll();
        return ResponseEntity.ok(allReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
