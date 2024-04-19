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

@Controller
public class ReservationController {

    private final ReservationMapper reservationMapper = new ReservationMapper();

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String getAdminReservations() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(reservationMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        Reservation reservation = reservationMapper.mapToReservation(request);
        long saveId = reservationRepository.save(reservation);

        ReservationResponse response = reservationMapper.mapToResponse(saveId, reservation);
        URI location = URI.create("/reservations/" + saveId);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
