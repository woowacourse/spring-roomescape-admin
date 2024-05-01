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
import roomescape.model.ReservationTimeDto;
import roomescape.model.ReservationTimeRequest;
import roomescape.model.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> listReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.findAll()
                .stream()
                .map(this::toResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeDto newReservationTimeDto = reservationTimeService.save(this.toDto(reservationTimeRequest));
        return ResponseEntity.ok(toResponse(newReservationTimeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    private ReservationTimeResponse toResponse(final ReservationTimeDto reservationTimeDto) {
        return new ReservationTimeResponse(reservationTimeDto.id(), reservationTimeDto.startAt());
    }

    private ReservationTimeDto toDto(final ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTimeDto(null, reservationTimeRequest.startAt());
    }

}
