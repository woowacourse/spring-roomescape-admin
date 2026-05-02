package roomescape.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationTimeService;
import roomescape.service.command.ReservationTimeCommand;
import roomescape.service.result.ReservationTimeResult;

@RestController
@RequestMapping("/times")
@Validated
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResult> register(@Valid @RequestBody ReservationTimeCommand request) {
        return ResponseEntity.ok(reservationTimeService.register(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResult>> getAllTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTimes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(
            @PathVariable
            @NotNull(message = "취소할 예약 시간 식별자는 필수 값입니다.")
            @Positive(message = "식별자는 양수여야 합니다.")
            Long id
    ) {
        reservationTimeService.remove(id);
        return ResponseEntity.ok().build();
    }
}
