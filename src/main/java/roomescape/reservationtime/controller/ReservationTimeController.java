package roomescape.reservationtime.controller;

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
import roomescape.reservationtime.dto.request.CreateReservationTimeRequest;
import roomescape.reservationtime.dto.response.FindReservationTimeResponse;
import roomescape.reservationtime.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<FindReservationTimeResponse>> getReservationTimes() {
        List<FindReservationTimeResponse> reservationTimeResponses = reservationTimeService.getReservationTimes();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindReservationTimeResponse> getReservationTime(@PathVariable final Long id) {
        return ResponseEntity.ok(reservationTimeService.getReservationTime(id));
    }

    @PostMapping
    public ResponseEntity<Void> createReservationTime(@RequestBody final CreateReservationTimeRequest createReservationTimeRequest) {
        Long id = reservationTimeService.createReservationTime(createReservationTimeRequest);
        return ResponseEntity.created(URI.create("/times/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable final Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
