package roomescape.reservation.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResultResponse;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResultResponse>> getReservations() {
        List<ReservationResultResponse> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResultResponse> createReservations(
            @RequestBody CreateReservationRequest createReservationRequest) {
        ReservationResultResponse reservedRoomId = reservationService.reserve(
                ReservationMapper.toReservation(createReservationRequest));
        return ResponseEntity.of(Optional.of(reservedRoomId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok(null);
    }
}
