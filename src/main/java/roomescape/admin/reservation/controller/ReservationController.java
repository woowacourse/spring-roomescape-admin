package roomescape.admin.reservation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.reservation.controller.dto.request.ReservationRequest;
import roomescape.admin.reservation.controller.dto.response.ReservationResponse;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll().stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.create(reservationRequest);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        int deleteCount = reservationService.delete(id);
        if (deleteCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 예약이 존재하지 않습니다.");
        }

        return ResponseEntity.noContent().build();
    }
}
