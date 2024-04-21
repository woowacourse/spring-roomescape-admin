package roomescape.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.request.CreateReservationTimeWebRequest;
import roomescape.controller.response.ReservationTimeWebResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeWebResponse> add(@RequestBody @Valid CreateReservationTimeWebRequest request) {
        ReservationTime newReservationTime = reservationTimeService.addTime(request.toServiceRequest());
        ReservationTimeWebResponse response = new ReservationTimeWebResponse(newReservationTime);
        URI uri = URI.create("/times/" + newReservationTime.getId());

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable long id) {
        reservationTimeService.deleteBy(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeWebResponse>> getTimes() {
        List<ReservationTime> times = reservationTimeService.findTimes();
        List<ReservationTimeWebResponse> responses = times.stream()
                .map(ReservationTimeWebResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
