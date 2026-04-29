package roomescape.reservation.presentation;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.application.TimeService;
import roomescape.reservation.presentation.dto.request.TimeSaveRequest;
import roomescape.reservation.presentation.dto.response.TimeFindResponse;
import roomescape.reservation.presentation.dto.response.TimeSaveResponse;

@RestController
@RequestMapping("/times")
@Validated
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeSaveResponse> saveTime(
            @RequestBody @Valid TimeSaveRequest body) {
        TimeSaveResponse response = timeService.saveTime(body);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeFindResponse>> findAllTimes() {
        List<TimeFindResponse> responses = timeService.findAllTimes();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
