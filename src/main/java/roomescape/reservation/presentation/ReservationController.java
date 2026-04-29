package roomescape.reservation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.presentation.dto.request.ReservationSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationSaveResponse;

@RestController
@RequestMapping("/reservations")
@Validated
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationSaveResponse> saveReservation(
            @RequestBody @Valid ReservationSaveRequest body) {
        ReservationSaveResponse response = reservationService.saveReservation(body);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
