package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeDto>> findReservationTimes() {
        List<ReservationTimeDto> reservationTimes = reservationTimeService.findReservationTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeDto newReservationTime = reservationTimeService.createReservationTime(reservationTimeRequest);
        return ResponseEntity.created(URI.create("/times/" + newReservationTime.id()))
                .body(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(value = "id") Long id) {
        boolean isDeleted = reservationTimeService.deleteReservationTime(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
