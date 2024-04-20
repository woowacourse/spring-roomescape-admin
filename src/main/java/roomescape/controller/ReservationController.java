package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationSaveRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService, final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<ReservationResponse> reservationResponses = reservationService.getReservations();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody final ReservationSaveRequest reservationSaveRequest) {
        try {
            final ReservationResponse reservationResponse = reservationService.saveReservation(reservationSaveRequest);
            return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.id()))
                    .body(reservationResponse);
        } catch (RuntimeException e) {
            logger.error("예약 저장 실패", e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(final @PathVariable("id") Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("예약 삭제 실패", e);
            return ResponseEntity.notFound().build();
        }
    }
}
