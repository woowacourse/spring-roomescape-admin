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
import roomescape.service.ReservationTimeService;
import roomescape.service.request.CreateReservationTimeRequest;
import roomescape.service.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationService;

    public ReservationTimeController(ReservationTimeService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody CreateReservationTimeRequest createReservationTImeRequest) {
        Long id = reservationService.create(createReservationTImeRequest);
        ReservationTimeResponse reservationTimeResponse = reservationService.findById(id);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationService.findAll();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("reservationTimeId") Long reservationTimeId) {
        reservationService.deleteById(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
