package roomescape.reservationtime.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;
import roomescape.reservationtime.service.ReservationTimeService;

@RestController
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> findAllReservationTimes() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAllReservationTimes();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> saveReservationTime(@Valid @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.saveReservationTime(reservationTimeRequest);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
