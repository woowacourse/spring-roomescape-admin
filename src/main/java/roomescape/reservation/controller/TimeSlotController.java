package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.TimeSlotRequest;
import roomescape.reservation.dto.TimeSlotResponse;
import roomescape.reservation.service.TimeSlotService;

@RestController
@RequestMapping("/times")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    public TimeSlotController(final TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<TimeSlotResponse> create(@RequestBody TimeSlotRequest timeSlotRequest) {
        return ResponseEntity.ok(timeSlotService.create(timeSlotRequest));
    }
}
