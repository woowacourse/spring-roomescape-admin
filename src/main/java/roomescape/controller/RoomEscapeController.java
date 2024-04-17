package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomEscapeController {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final ReservationMapper reservationMapper = new ReservationMapper();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String getAdminReservations() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        var reservationResponses = reservationRepository.findAll()
                .stream()
                .map(reservationMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = reservationMapper.mapToReservation(id, request);
        reservationRepository.save(reservation);

        ReservationResponse response = reservationMapper.mapToResponse(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
