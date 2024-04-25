package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestV2;
import roomescape.dto.ReservationResponseV2;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseV2> getReservations2() {
        return reservationService.findAll2();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseV2> save(@RequestBody final ReservationRequestV2 reservationRequestV2) {
        final ReservationResponseV2 reservationResponse = reservationService.save(reservationRequestV2);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        try {
            reservationService.remove(id);
            return ResponseEntity.ok()
                    .build();
        } catch (final IllegalArgumentException exception) {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
