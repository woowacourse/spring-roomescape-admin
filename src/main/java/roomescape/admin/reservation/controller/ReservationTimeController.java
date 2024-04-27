package roomescape.admin.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.reservation.controller.dto.request.ReservationTimeRequest;
import roomescape.admin.reservation.controller.dto.response.ReservationTimeResponse;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.service.ReservationTimeService;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> reservationTimeResponse = reservationTimeService.findAll().stream()
                .map(ReservationTimeResponse::from).toList();

        return ResponseEntity.ok(reservationTimeResponse);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeService.create(reservationTimeRequest.toReservationTime());

        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationTimeService.delete(id);

        return ResponseEntity.ok().build();
    }
}
