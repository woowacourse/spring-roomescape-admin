package roomescape.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationService;
import roomescape.service.command.ReservationCommand;
import roomescape.service.result.ReservationResult;

@RestController
@RequestMapping("/reservations")
@Validated
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResult> reserve(@Valid @RequestBody ReservationCommand request) {
        return ResponseEntity.ok(reservationService.reserve(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResult>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable
            @NotNull(message = "취소할 예약 식별자는 필수 값입니다.")
            @Positive(message = "식별자는 양수여야 합니다.")
            Long id
    ) {
        reservationService.cancelAllReservation(id);
        return ResponseEntity.ok().build();
    }
}
