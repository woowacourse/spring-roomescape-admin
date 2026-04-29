package roomescape.controller.web;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    ResponseEntity<ReservationTimeResponse> enrollTime(@RequestBody ReservationTimeRequest requestBody) {
        ReservationTime result = reservationTimeService.enrollReservationTimes(requestBody.startAt());

        ReservationTimeResponse responseBody = new ReservationTimeResponse(
                result.id(),
                result.startAt()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<ReservationTimeResponse>> queryReservationTimes() {
        List<ReservationTime> result = reservationTimeService.findAllReservationTimes();

        List<ReservationTimeResponse> foundReservationTimes = result.stream()
                .map(this::parseReservationTimeToReservationTimeResponse)
                .toList();

        return new ResponseEntity<>(
                foundReservationTimes,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteSpecificReservationTime(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ReservationTimeResponse parseReservationTimeToReservationTimeResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.id(),
                reservationTime.startAt()
        );
    }
}
