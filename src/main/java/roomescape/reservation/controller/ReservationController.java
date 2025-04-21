package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> allReservations = reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();

        return ResponseEntity.ok(allReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = new Reservation(requestDto.name(), requestDto.date(), requestDto.time());
        Reservation saved = reservationRepository.save(reservation);
        ReservationResponseDto responseDto = ReservationResponseDto.toDto(saved);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
