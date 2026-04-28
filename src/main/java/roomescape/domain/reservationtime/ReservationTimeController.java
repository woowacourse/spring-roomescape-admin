package roomescape.domain.reservationtime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservationtime.dto.CreateTimeRequest;
import roomescape.domain.reservationtime.dto.CreateTimeResponse;

@RestController
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping("/times")
    public ResponseEntity<CreateTimeResponse> createReservationTime(@RequestBody CreateTimeRequest request) {
        CreateTimeResponse response = reservationTimeService.createReservationTime(request);
        return ResponseEntity.ok(response);
    }
}
