package roomescape.controller;

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
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<Void> addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long savedId = reservationTimeService.addReservationTime(reservationTimeRequest);
        String redirectUrl = "/times/" + savedId;
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", redirectUrl).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllReservationTimes() {
        List<ReservationTimeResponse> reservationResponses = reservationTimeService.getAllReservationTimes();
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTimeResponse> getReservationTime(@PathVariable Long id) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.getReservationTime(id);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
