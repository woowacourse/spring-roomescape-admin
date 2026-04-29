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
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeCommand;
import roomescape.dto.AddReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTime();
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return new ResponseEntity<>(reservationTimeResponses, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody AddReservationTimeRequest addReservationTimeRequest) {
        ReservationTimeCommand reservationTimeCommand = new ReservationTimeCommand(addReservationTimeRequest.startAt());
        ReservationTime reservationTime = reservationTimeService.addReservationTime(reservationTimeCommand);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(reservationTime);

        return new ResponseEntity<>(reservationTimeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") long id) {
        int deletedCount = reservationTimeService.deleteReservationTime(id);

        if(deletedCount == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
