package roomescape.reservationtime;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.findAll();
        List<ReservationTimeResponse> responses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @Valid @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeService.save(reservationTimeRequest.startAt());
        return ResponseEntity.ok(ReservationTimeResponse.from(reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservationTime(@PathVariable long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}

