package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    ResponseEntity<ReservationTimeResponse> enrollTime(@RequestBody ReservationTimeRequest requestBody) {
        ReservationTime result = reservationTimeService.save(requestBody.startAt());

        ReservationTimeResponse responseBody = new ReservationTimeResponse(
                result.id(),
                result.startAt()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<ReservationTimeResponse>> queryReservationTimes() {
        List<ReservationTime> result = reservationTimeService.findAll();

        List<ReservationTimeResponse> foundReservationTimes = result.stream()
                .map(ReservationTimeResponse::fromDomain)
                .toList();

        return new ResponseEntity<>(
                foundReservationTimes,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteSpecificReservationTime(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
