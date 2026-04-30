package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto requestDto) {
        Long id = reservationRepository.save(requestDto);
        Reservation savedReservation = reservationRepository.findById(id);
        return ResponseEntity.ok(savedReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationRepository.delete(id);
    }
}

