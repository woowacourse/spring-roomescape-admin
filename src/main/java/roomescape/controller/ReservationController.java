package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Member;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong atomicLong = new AtomicLong(1L);
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> postReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation(
                atomicLong.getAndIncrement(),
                new Member(reservationRequestDto.getName()),
                reservationRequestDto.getDate(),
                reservationRequestDto.getTime()
        );
        reservationRepository.save(reservation);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(
                reservation.getId(),
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
