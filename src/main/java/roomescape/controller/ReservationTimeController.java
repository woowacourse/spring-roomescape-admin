package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@Controller
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> read() {
        List<ReservationTimeResponse> response = reservationTimeService.findAllReservationTimes();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.createReservationTime(request);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}
