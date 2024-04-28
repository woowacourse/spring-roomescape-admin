package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.service.ReservationTimeService;

import java.net.URI;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    // TODO: Controller 메서드 명 깔끔하게 수정
    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addReservationTime(@RequestBody final ReservationTimeRequestDto request) {
        ReservationTimeResponseDto response = reservationTimeService.addReservationTime(request);

        return ResponseEntity.created(URI.create("/times/" + response.id()))
                .body(response);
    }
}
