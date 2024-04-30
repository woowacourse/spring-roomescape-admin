package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.request.ReservationTimeRequestDto;
import roomescape.reservation.dto.response.ReservationTimeResponseDto;
import roomescape.reservation.dto.response.ReservationTimesResponseDto;
import roomescape.reservation.service.ReservationTimeService;

import java.net.URI;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<ReservationTimesResponseDto> getAllReservationTimes() {
        ReservationTimesResponseDto response = reservationTimeService.findAllReservationTime();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> saveReservationTime(@RequestBody final ReservationTimeRequestDto request) {
        ReservationTimeResponseDto response = reservationTimeService.addReservationTime(request);

        return ResponseEntity.created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> removeReservationTime(@PathVariable final Long id) {
        reservationTimeService.removeReservationTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
