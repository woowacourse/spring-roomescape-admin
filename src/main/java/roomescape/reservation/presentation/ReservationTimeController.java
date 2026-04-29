package roomescape.reservation.presentation;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.application.ReservationTimeService;
import roomescape.reservation.presentation.dto.request.ReservationTimeSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationTimeFindResponse;
import roomescape.reservation.presentation.dto.response.ReservationTimeSaveResponse;

@RestController
@RequestMapping("/times")
@Validated
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<ReservationTimeSaveResponse> saveReservationTime(
            @RequestBody @Valid ReservationTimeSaveRequest body) {
        ReservationTimeSaveResponse response = reservationTimeService.saveReservationTime(body);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeFindResponse>> findAllReservationTimes() {
        List<ReservationTimeFindResponse> responses = reservationTimeService.findAllReservationTimes();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id){
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
