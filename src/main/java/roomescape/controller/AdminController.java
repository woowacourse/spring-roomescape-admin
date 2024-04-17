package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class AdminController {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        var reservationResponses = reservationRepository.findAll()
                .stream()
                .map(Reservation::toDto)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        var reservation = request.toReservation(index.getAndIncrement());
        reservationRepository.save(reservation);
        return ResponseEntity.ok(reservation.toDto());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약이 없습니다."));
        reservationRepository.deleteById(reservation.getId());
        return ResponseEntity.ok().build();
    }
}
